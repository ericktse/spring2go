package com.spring2go.file.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @description: 文件服务类型
 * @author: xiaobin
 * @date: 2021-05-21 9:37
 */
@Getter
@RequiredArgsConstructor
public enum FileServiceType {
    /**
     * 本地存储(默认)
     */
    LOCAL("LOCAL", "本地存储"),
    /**
     * FastDfs存储
     */
    FASTDFS("FASTDFS", "FastDfs存储"),
    /**
     * MinIO存储
     */
    MINIO("MINIO", "MinIO存储"),
    /**
     * 阿里云OSS
     */
    ALIOSS("ALIOSS", "阿里云OSS");

    /**
     * 编码
     */
    private final String code;
    /**
     * 名称
     */
    private final String name;

}
