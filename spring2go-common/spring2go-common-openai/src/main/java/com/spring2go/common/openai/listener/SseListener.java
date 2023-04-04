package com.spring2go.common.openai.listener;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.spring2go.common.openai.entity.ChatCompletionResponse;
import com.spring2go.common.openai.entity.Choice;
import com.spring2go.common.openai.entity.Message;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Server Send Event Listener
 *
 * @author xiaobin
 */
@Slf4j
public class SseListener extends EventSourceListener {

    private static final String DONE_FLAG = "[DONE]";
    private final SseEmitter sseEmitter;
    private String lastMessage;

    /**
     * Called when all new message are received.
     *
     * @param msg the new message
     */
    private Consumer<String> completionCallback = (msg) -> {
        log.debug(msg);
    };

    public SseListener() {
        sseEmitter = new SseEmitter(-1L);
    }

    public SseListener(SseEmitter emitter) {
        sseEmitter = emitter;
    }

    public synchronized void onCompletion(Consumer<String> callback) {
        this.completionCallback = callback;
    }

    public SseEmitter getSseEmitter() {
        return this.sseEmitter;
    }

    @Override
    public void onOpen(@NotNull EventSource eventSource, @NotNull Response response) {
        super.onOpen(eventSource, response);
    }

    @Override
    public void onClosed(@NotNull EventSource eventSource) {
        super.onClosed(eventSource);
    }

    @SneakyThrows
    @Override
    public void onEvent(@NotNull EventSource eventSource, @Nullable String id, @Nullable String type, @NotNull String data) {
        if (DONE_FLAG.equals(data)) {
            completionCallback.accept(lastMessage);
            return;
        }

        ChatCompletionResponse response = JSON.parseObject(data, ChatCompletionResponse.class);
        List<Choice> choices = response.getChoices();
        if (choices == null || choices.isEmpty()) {
            return;
        }
        Message delta = choices.get(0).getDelta();
        String text = delta.getContent();

        if (text != null) {
            lastMessage += text;
            sseEmitter.send(text);
        }
    }

    @Override
    public void onFailure(@NotNull EventSource eventSource, @Nullable Throwable t, @Nullable Response response) {
        try {
            String responseText = "";
            if (Objects.nonNull(response)) {
                responseText = response.body().string();
            }

            String forbiddenText = "Your access was terminated due to violation of our policies";
            String overloadedText = "That model is currently overloaded with other requests.";
            if (StrUtil.contains(responseText, forbiddenText)) {
                log.error("检测到号被封了");
            } else if (StrUtil.contains(responseText, overloadedText)) {
                log.error("检测到官方超载了，赶紧优化你的代码，做重试吧");
            } else {
                log.error("response：{}", responseText, t);
            }

            sseEmitter.complete();

        } catch (Exception e) {
            log.error("onFailure error:{}", e);
            // do nothing
        } finally {
            eventSource.cancel();
        }
    }
}
