package com.spring2go.file.service;

import com.spring2go.common.core.util.DateUtils;
import com.spring2go.file.config.FileProperties;
import com.spring2go.file.domain.FileServiceType;
import com.spring2go.file.domain.FileStorageType;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @description: MinIO文件存储
 * @author: xiaobin
 * @date: 2021-05-21 9:42
 */
@RequiredArgsConstructor
public class MinioFileStorageClient implements FileStorageClient {

    private final MinioClient minioClient;

    @Override
    public String uploadFile(MultipartFile file, FileProperties fileProperties) throws Exception {
        //生成文件名
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String fileName = DateUtils.dateTimeNow("yyyyMMdd") + "/" + UUID.randomUUID().toString().replace("-", "") + "." + extension;

        PutObjectArgs args = PutObjectArgs.builder()
                .bucket(fileProperties.getMinio().getBucketName())
                .object(fileName)
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType())
                .build();
        minioClient.putObject(args);
        String url = fileProperties.getMinio().getUrl() + "/" + fileProperties.getMinio().getBucketName() + "/" + fileName;

        return url;
    }

    @Override
    public FileServiceType getType() {
        return FileServiceType.MINIO;
    }
}
