package com.spring2go.common.openai;

import cn.hutool.http.ContentType;
import cn.hutool.http.Header;
import com.alibaba.fastjson2.JSON;
import com.spring2go.common.openai.constant.OpenAiConstants;
import com.spring2go.common.openai.entity.BaseResponse;
import com.spring2go.common.openai.entity.ChatCompletion;
import com.spring2go.common.openai.entity.ChatCompletionResponse;
import com.spring2go.common.openai.entity.Message;
import com.spring2go.common.openai.exception.OpenAiException;
import lombok.Builder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.net.Proxy;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * OpenAI服务
 *
 * @author xiaobin
 */
@Builder
@Slf4j
public class OpenAiService {
    /**
     * 超时 默认300s
     */
    private static final long TIMEOUT = 300;

    /**
     * keys
     */
    private String apiKey;

    private OkHttpClient okHttpClient;

    /**
     * okhttp 代理
     */
    @Builder.Default
    private Proxy proxy = Proxy.NO_PROXY;

    public OpenAiService initialize() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
//        client.addInterceptor(chain -> {
//            Request original = chain.request();
//            String key = apiKey;
//
//            Request request = original.newBuilder()
//                    .header(Header.AUTHORIZATION.getValue(), "Bearer " + key)
//                    .header(Header.CONTENT_TYPE.getValue(), ContentType.JSON.getValue())
//                    .method(original.method(), original.body())
//                    .build();
//            return chain.proceed(request);
//        })
        client.addInterceptor(chain -> {
            Request original = chain.request();
            Response response = chain.proceed(original);
            if (!response.isSuccessful()) {
                String errorMsg = response.body().string();

                log.error("请求异常：{}", errorMsg);
                BaseResponse baseResponse = JSON.parseObject(errorMsg, BaseResponse.class);
                if (Objects.nonNull(baseResponse.getError())) {
                    log.error(baseResponse.getError().getMessage());
                    throw new OpenAiException(baseResponse.getError().getMessage());
                }
                throw new OpenAiException("OpenAI error");
            }
            return response;
        });

        client.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
        client.writeTimeout(TIMEOUT, TimeUnit.SECONDS);
        client.readTimeout(TIMEOUT, TimeUnit.SECONDS);
        if (Objects.nonNull(proxy)) {
            client.proxy(proxy);
        }
        OkHttpClient httpClient = client.build();
        this.okHttpClient = httpClient;

        return this;
    }

    @SneakyThrows
    public ChatCompletionResponse chatCompletion(ChatCompletion chatCompletion) {

        MediaType type = MediaType.get(ContentType.build(ContentType.JSON.toString(), StandardCharsets.UTF_8));
        RequestBody body = RequestBody.create(type, JSON.toJSONString(chatCompletion));

        Request request = new Request.Builder()
                .header(Header.AUTHORIZATION.getValue(), "Bearer " + apiKey)
                .header(Header.CONTENT_TYPE.getValue(), ContentType.JSON.getValue())
                .url(OpenAiConstants.CHAT_COMPLETION_API)
                .post(body)
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            ChatCompletionResponse chatCompletionResponse = JSON.parseObject(response.body().string(), ChatCompletionResponse.class);
            return chatCompletionResponse;
        } catch (Exception e) {
            throw e;
        }
    }

    public String chat(String message) {
        ChatCompletion chatCompletion = ChatCompletion.builder()
                .messages(Arrays.asList(Message.of(message)))
                .build();
        ChatCompletionResponse response = this.chatCompletion(chatCompletion);
        return response.getChoices().get(0).getMessage().getContent();
    }
}
