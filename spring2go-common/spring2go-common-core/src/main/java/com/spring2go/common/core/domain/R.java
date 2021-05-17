package com.spring2go.common.core.domain;

import java.io.Serializable;

import com.spring2go.common.core.constant.CommonConstants;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * @Description: 公共Response类
 * @author: xiaobin
 * @date: 2021-03-29 17:07
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 成功
     */
    public static final int SUCCESS = CommonConstants.SUCCESS;

    /**
     * 失败
     */
    public static final int FAIL = CommonConstants.FAIL;

    /**
     * 失败
     */
    public static final int UN_AUTHORIZED = CommonConstants.UN_AUTHORIZED;

    @Getter
    @Setter
    private int code;

    @Getter
    @Setter
    private String msg;

    @Getter
    @Setter
    private T data;

    public static <T> R<T> ok() {
        return restResult(null, CommonConstants.SUCCESS, null);
    }

    public static <T> R<T> ok(T data) {
        return restResult(data, CommonConstants.SUCCESS, null);
    }

    public static <T> R<T> ok(T data, String msg) {
        return restResult(data, CommonConstants.SUCCESS, msg);
    }

    public static <T> R<T> failed() {
        return restResult(null, CommonConstants.FAIL, null);
    }

    public static <T> R<T> failed(String msg) {
        return restResult(null, CommonConstants.FAIL, msg);
    }

    public static <T> R<T> failed(T data) {
        return restResult(data, CommonConstants.FAIL, null);
    }

    public static <T> R<T> failed(T data, String msg) {
        return restResult(data, CommonConstants.FAIL, msg);
    }

    public static <T> R<T> unauthorized(String msg) {
        return restResult(null, CommonConstants.UN_AUTHORIZED, msg);
    }

    private static <T> R<T> restResult(T data, int code, String msg) {
        R<T> apiResult = new R<T>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }
}