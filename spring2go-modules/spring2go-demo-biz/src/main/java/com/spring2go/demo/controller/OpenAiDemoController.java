package com.spring2go.demo.controller;

import com.spring2go.common.core.util.StringUtils;
import com.spring2go.common.openai.OpenAiService;
import com.spring2go.common.openai.listener.SseListener;
import com.spring2go.common.openai.util.Proxys;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.net.Proxy;

/**
 * OpenAI Demo
 *
 * @author xiaobin
 */
@Api(tags = "OpenAI示例")
@RestController
public class OpenAiDemoController {

    @RequestMapping("/chat")
    public String chat(String input) {
        OpenAiService openAiService = initOpenAiService();

        input = StringUtils.isEmpty(input) ? "写一段七言绝句诗" : input;
        String res = openAiService.chat(input);
        return res;
    }

    @RequestMapping("/chatContext")
    public String chatContext(String input) {
        OpenAiService openAiService = initOpenAiService();

        input = StringUtils.isEmpty(input) ? "写一段七言绝句诗" : input;
        String res = openAiService.chatContext(input, "chat1");
        return res;
    }

    @RequestMapping("/chat/sse")
    public SseEmitter sseEmitter(String input) {
        OpenAiService openAiService = initOpenAiService();
        input = StringUtils.isEmpty(input) ? "写一段七言绝句诗" : input;

        SseListener listener = new SseListener();
        listener.onCompletion(msg -> {
            //回答完成，可以做一些事情
            System.out.println(msg);
        });
        openAiService.streamChat(input, listener);

        return listener.getSseEmitter();
    }

    private OpenAiService initOpenAiService() {
        Proxy proxy = Proxys.http("127.0.0.1", 7890);
        OpenAiService openAiService = OpenAiService.builder()
                .apiKey("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")
                .proxy(proxy)
                .build()
                .initialize();

        return openAiService;
    }
}
