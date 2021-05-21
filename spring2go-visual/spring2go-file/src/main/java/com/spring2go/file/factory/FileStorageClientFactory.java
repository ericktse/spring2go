package com.spring2go.file.factory;

import com.spring2go.common.core.util.SpringContextHolder;
import com.spring2go.file.config.FileProperties;
import com.spring2go.file.domain.FileServiceType;
import com.spring2go.file.service.FileStorageClient;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 文件服务
 * @author: xiaobin
 * @date: 2021-05-20 18:22
 */
public class FileStorageClientFactory {
    private Map<String, FileStorageClient> fileServiceMap;

    @Setter
    @Getter
    private FileProperties fileProperties;

    public FileStorageClientFactory(FileProperties fileProperties) {
        this.fileProperties = fileProperties;
    }

    public FileStorageClient createFileStorageClient() {
        FileServiceType type = fileProperties.getType();
        if (fileServiceMap == null) {
            initMap();
        }
        FileStorageClient fileStorageClient = fileServiceMap.get(type.getCode());
        return fileStorageClient;
    }

    private void initMap() {
        fileServiceMap = new HashMap<>();
        Map<String, FileStorageClient> services = SpringContextHolder.getApplicationContext().getBeansOfType(FileStorageClient.class);
        services.forEach((k, v) -> {
            fileServiceMap.put(v.getType().getCode(), v);
        });
    }
}
