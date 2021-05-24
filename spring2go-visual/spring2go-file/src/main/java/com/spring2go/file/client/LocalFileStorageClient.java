package com.spring2go.file.client;

import com.spring2go.common.core.util.DateUtils;
import com.spring2go.file.config.FileProperties;
import com.spring2go.file.exception.FileException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;
import java.util.UUID;

/**
 * @description: 本地文件存储(默认)
 * @author: xiaobin
 * @date: 2021-05-20 16:34
 */
public class LocalFileStorageClient implements FileStorageClient {

    /**
     * 默认大小 50M
     */
    public static final long DEFAULT_MAX_SIZE = 50 * 1024 * 1024;

    /**
     * 默认的文件名最大长度 100
     */
    public static final int DEFAULT_FILE_NAME_LENGTH = 100;

    /**
     * 默认可上传文件格式
     */
    public static final String[] DEFAULT_ALLOWED_EXTENSION = {
            // 图片
            "bmp", "gif", "jpg", "jpeg", "png",
            // word excel powerpoint
            "doc", "docx", "xls", "xlsx", "ppt", "pptx", "html", "htm", "txt",
            // 压缩文件
            "rar", "zip", "gz", "bz2",
            // 视频格式
            "mp4", "avi", "rmvb",
            // pdf
            "pdf"};


    @Override
    public String uploadFile(MultipartFile file, FileProperties fileProperties) throws Exception {

        String fileName = file.getOriginalFilename();
        int fileNameLength = fileName.length();
        if (fileNameLength > DEFAULT_FILE_NAME_LENGTH) {
            throw new FileException("upload.filename.exceed.length: " + DEFAULT_FILE_NAME_LENGTH);
        }

        //校验文件
        assertAllowed(file, DEFAULT_ALLOWED_EXTENSION);

        //生成文件名
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        fileName = DateUtils.dateTimeNow("yyyyMMdd") + "/" + UUID.randomUUID().toString().replace("-", "") + "." + extension;

        //保持本地
        File desc = getAbsoluteFile(fileProperties.getLocal().getPath(), fileName);
        file.transferTo(desc);

        //生成文件路径
        String url = fileProperties.getLocal().getDomain() + fileProperties.getLocal().getBucketName() + "/" + fileName;

        return url;
    }

    /**
     * 文件大小校验
     *
     * @param file 上传的文件
     * @throws FileException 文件校验异常
     */
    private void assertAllowed(MultipartFile file, String[] allowedExtension) throws FileException {
        long size = file.getSize();
        if (DEFAULT_MAX_SIZE != -1 && size > DEFAULT_MAX_SIZE) {
            throw new FileException("upload.exceed.maxSize: " + DEFAULT_MAX_SIZE / 1024 / 1024);
        }

        String fileName = file.getOriginalFilename();
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        if (allowedExtension != null && !isAllowedExtension(extension, allowedExtension)) {
            throw new FileException("filename : [" + fileName + "], extension : [" + extension + "], allowed extension : [" + Arrays.toString(allowedExtension) + "]");
        }
    }

    /**
     * 判断MIME类型是否是允许的MIME类型
     *
     * @param extension        上传文件类型
     * @param allowedExtension 允许上传文件类型
     * @return true/false
     */
    private boolean isAllowedExtension(String extension, String[] allowedExtension) {
        for (String str : allowedExtension) {
            if (str.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取绝对文件
     *
     * @param path:
     * @param fileName:
     * @return java.io.File
     */
    private File getAbsoluteFile(String path, String fileName) {
        File desc = new File(path + File.separator + fileName);

        if (!desc.exists()) {
            if (!desc.getParentFile().exists()) {
                desc.getParentFile().mkdirs();
            }
        }
        return desc.isAbsolute() ? desc : desc.getAbsoluteFile();
    }
}
