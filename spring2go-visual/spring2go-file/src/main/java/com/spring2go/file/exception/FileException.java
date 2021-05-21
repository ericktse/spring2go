package com.spring2go.file.exception;

import lombok.Data;

/**
 * @description: 自定义文件运行时异常
 * @author: xiaobin
 * @date: 2021-05-21 9:57
 */
@Data
public class FileException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public FileException() {
    }

    public FileException(String msg) {
        super(msg);
    }

    public FileException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public FileException(Throwable cause) {
        super(cause);
    }
}
