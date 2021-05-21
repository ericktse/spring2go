package com.spring2go.file.config;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.spring2go.file.domain.FileStorageType;
import com.spring2go.file.service.AliossFileStorageClient;
import com.spring2go.file.service.FastDfsFileStorageClient;
import com.spring2go.file.service.LocalFileStorageClient;
import com.spring2go.file.service.MinioFileStorageClient;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 文件服务自动配置
 * @author: xiaobin
 * @date: 2021-05-21 9:45
 */
@Configuration
@EnableConfigurationProperties({FileProperties.class})
@Slf4j
public class FileServiceAutoConfiguration {

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnProperty(name = {"spring2go.file.type"}, havingValue = FileStorageType.LOCAL)
    protected static class LocalStorageConfiguration {
        protected LocalStorageConfiguration() {
        }

        @Bean
        public LocalFileStorageClient localFileStorageClient() {
            return new LocalFileStorageClient();
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnProperty(name = {"spring2go.file.type"}, havingValue = FileStorageType.MINIO)
    protected static class MinioStorageConfiguration {
        protected MinioStorageConfiguration() {
        }

        @Bean
        @ConditionalOnMissingBean(MinioClient.class)
        public MinioClient minioClient(FileProperties fileProperties) {
            FileProperties.MinioFileProperties minioFileProperties = fileProperties.getMinio();
            return MinioClient.builder().endpoint(minioFileProperties.getUrl()).credentials(minioFileProperties.getAccessKey(), minioFileProperties.getSecretKey()).build();
        }

        @Bean
        public MinioFileStorageClient minioFileStorageClient(MinioClient minioClient) {
            return new MinioFileStorageClient(minioClient);
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnProperty(name = {"spring2go.file.type"}, havingValue = FileStorageType.FASTDFS)
    protected static class FastdfsStorageConfiguration {
        protected FastdfsStorageConfiguration() {
        }

        @Bean
        public FastDfsFileStorageClient fastDfsFileStorageClient(FastFileStorageClient storageClient) {
            return new FastDfsFileStorageClient(storageClient);
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnProperty(name = {"spring2go.file.type"}, havingValue = FileStorageType.ALIOSS)
    protected static class AliossStorageConfiguration {
        protected AliossStorageConfiguration() {
        }

        @Bean
        @ConditionalOnMissingBean(OSSClient.class)
        public OSSClient alioss(FileProperties fileProperties) {
            FileProperties.AliossFileProperties aliossFileProperties = fileProperties.getAlioss();
            OSSClient ossClient = new OSSClient(aliossFileProperties.getEndpoint(),
                    new DefaultCredentialProvider(aliossFileProperties.getAccessKey(), aliossFileProperties.getSecretKey()),
                    new ClientConfiguration());
            return ossClient;
        }

        @Bean
        public AliossFileStorageClient aliossFileStorageClient(OSSClient ossClient) {
            return new AliossFileStorageClient(ossClient);
        }
    }
}
