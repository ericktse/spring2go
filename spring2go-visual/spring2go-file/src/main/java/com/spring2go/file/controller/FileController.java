package com.spring2go.file.controller;

import com.spring2go.common.core.domain.R;
import com.spring2go.file.config.FileProperties;
import com.spring2go.file.factory.FileStorageClientFactory;
import com.spring2go.file.service.FileStorageClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 附件上传endpoint
 * @author: xiaobin
 * @date: 2021-05-20 16:25
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/file")
public class FileController {

    private final FileStorageClientFactory fileStorageClientFactory;

    private final FileStorageClient client;
    private final FileProperties fileProperties;

    /**
     * 上传文件
     * 文件名采用uuid,避免原始文件名中带"-"符号导致下载的时候解析出现异常
     *
     * @param file 资源
     * @return R
     */
    @PostMapping("/upload")
    public R upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        try {
//            FileStorageClient client = fileStorageClientFactory.createFileStorageClient();
//            String url = client.uploadFile(file, fileStorageClientFactory.getFileProperties());
//
            String url = client.uploadFile(file, fileProperties);
            return R.ok(url);
        } catch (Exception e) {
            log.error("上传文件失败", e);
            return R.failed("上传文件失败：" + e.getMessage());
        }
    }
}
