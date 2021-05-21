package com.spring2go.file.domain;

/**
 * TODO
 *
 * @author: xiaobin
 * @date: 2021-05-21 16:36
 */
public final class FileStorageType {

    /**
     * 本地存储
     */
    public static final String LOCAL = "LOCAL";
    /**
     * FastDfs存储
     */
    public static final String FASTDFS = "FASTDFS";
    /**
     * MinIO存储
     */
    public static final String MINIO = "MINIO";
    /**
     * 阿里云OSS
     */
    public static final String ALIOSS = "ALIOSS";

    private FileStorageType() {
    }
}