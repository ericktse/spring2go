package com.spring2go.file.service;

import com.spring2go.file.config.FileProperties;
import com.spring2go.file.domain.FileServiceType;
import org.springframework.web.multipart.MultipartFile;


/**
 * @description: 文件服务接口
 * @author: xiaobin
 * @date: 2021-05-20 16:33
 */
public interface FileStorageClient {

    /**
     * 文件上传接口
     *
     * @param file 上传的文件
     * @return 访问地址
     * @throws Exception
     */
    String uploadFile(MultipartFile file, FileProperties fileProperties) throws Exception;

    /**
     * 文件存储类型
     */
    FileServiceType getType();
}
