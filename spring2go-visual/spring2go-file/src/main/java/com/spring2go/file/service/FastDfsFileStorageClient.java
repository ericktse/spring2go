package com.spring2go.file.service;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.spring2go.file.config.FileProperties;
import com.spring2go.file.domain.FileServiceType;
import com.spring2go.file.domain.FileStorageType;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


/**
 * @description: FastDFS文件存储服务
 * @author: xiaobin
 * @date: 2021-05-21 9:35
 */
@RequiredArgsConstructor
public class FastDfsFileStorageClient implements FileStorageClient {

    private final FastFileStorageClient storageClient;

    @Override
    public String uploadFile(MultipartFile file, FileProperties fileProperties) throws Exception {
        StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(),
                FilenameUtils.getExtension(file.getOriginalFilename()), null);
        String url = fileProperties.getFastdfs().getDomain() + "/" + storePath.getFullPath();
        return url;
    }

    @Override
    public FileServiceType getType() {
        return FileServiceType.FASTDFS;
    }
}
