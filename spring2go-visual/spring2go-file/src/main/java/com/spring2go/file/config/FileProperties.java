package com.spring2go.file.config;

import com.spring2go.file.domain.FileServiceType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description: 文件服务自定义配置
 * @author: xiaobin
 * @date: 2021-05-21 9:46
 */
@Data
@ConfigurationProperties("spring2go.file")
public class FileProperties {

    /**
     * 存储类型
     */
    private FileServiceType type;

    /**
     * 本地存储
     */
    private LocalFileProperties local = new LocalFileProperties();

    /**
     * FastDFS存储
     */
    private FastDfsFileProperties fastdfs = new FastDfsFileProperties();

    /**
     * MinIO存储
     */
    private MinioFileProperties minio = new MinioFileProperties();

    /**
     * AliOSS存储
     */
    private AliossFileProperties alioss = new AliossFileProperties();

    /**
     * 本地存储配置
     *
     * @author: xiaobin
     * @date: 2021/5/21 14:00
     */
    @Data
    public class LocalFileProperties {
        /**
         * 链接地址
         */
        private String domain;

        /**
         * 存储路径
         */
        private String path;

        /**
         * 文件桶
         */
        private String bucket;

    }

    /**
     * FastDFS存储配置
     *
     * @author: xiaobin
     * @date: 2021/5/21 14:00
     */
    @Data
    public class FastDfsFileProperties {
        /**
         * 链接地址
         */
        private String domain;

    }

    /**
     * MinIO存储配置
     *
     * @author: xiaobin
     * @date: 2021/5/21 14:00
     */
    @Data
    public class MinioFileProperties {
        /**
         * 服务地址
         */
        private String url;

        /**
         * 用户名
         */
        private String accessKey;

        /**
         * 密码
         */
        private String secretKey;

        /**
         * 存储桶名称
         */
        private String bucketName;

    }

    /**
     * AliOSS存储配置
     *
     * @author: xiaobin
     * @date: 2021/5/21 14:00
     */
    @Data
    public class AliossFileProperties {
        /**
         * 服务地址
         */
        private String endpoint;

        /**
         * 用户名
         */
        private String accessKey;

        /**
         * 密码
         */
        private String secretKey;

        /**
         * 存储桶名称
         */
        private String bucketName;

        /**
         * 静态地址
         */
        private String staticDomain;

    }
}
