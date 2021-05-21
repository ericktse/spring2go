package com.spring2go.file.service;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;
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

import java.io.IOException;
import java.util.UUID;

/**
 * @description: 阿里云OSS文件存储
 * @author: xiaobin
 * @date: 2021-05-21 9:43
 */
@RequiredArgsConstructor
public class AliossFileStorageClient implements FileStorageClient {

    private final OSSClient ossClient;

    @Override
    public String uploadFile(MultipartFile file, FileProperties fileProperties) throws IOException {

        //生成文件名
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String fileName = DateUtils.dateTimeNow("yyyyMMdd") + "/" + UUID.randomUUID().toString().replace("-", "") + "." + extension;

        String newBucket = fileProperties.getAlioss().getBucketName();
        //判断桶是否存在,不存在则创建桶
        if (!ossClient.doesBucketExist(newBucket)) {
            ossClient.createBucket(newBucket);
        }
        String url = fileProperties.getAlioss().getStaticDomain() + "/" + fileName;
        ossClient.putObject(newBucket, url, file.getInputStream());
        return url;
    }

    @Override
    public FileServiceType getType() {
        return FileServiceType.ALIOSS;
    }
}
