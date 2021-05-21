package com.spring2go.file.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/**
 * 通用映射配置
 *
 * @author: xiaobin
 * @date: 2021-05-21 11:00
 */
@Configuration
@RequiredArgsConstructor
public class ResourceConfigurer implements WebMvcConfigurer {

    private final FileProperties fileProperties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //映射上传静态文件到文件目录
        registry.addResourceHandler(fileProperties.getLocal().getBucket() + "/**")
                .addResourceLocations("file:" + fileProperties.getLocal().getPath() + File.separator);
    }
}
