package com.spring2go.demo.controller;

import com.spring2go.common.core.util.StringUtils;
import com.spring2go.common.openai.OpenAiService;
import com.spring2go.common.openai.util.Proxys;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.Proxy;

/**
 * OpenAI Demo
 *
 * @author xiaobin
 */
@Api(tags = "OpenAI示例")
@RestController("/openai")
public class OpenAiDemoController {

    @RequestMapping("/chat")
    public String chat(String input) {
        Proxy proxy = Proxys.http("127.0.0.1", 7890);
        OpenAiService openAiService = OpenAiService.builder()
                .apiKey("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")
                .proxy(proxy)
                .build()
                .initialize();

        input = StringUtils.isEmpty(input) ? "写一段七言绝句诗" : input;
        String res = openAiService.chat(input);
        return res;
    }
}
