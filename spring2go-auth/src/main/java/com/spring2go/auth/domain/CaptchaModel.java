package com.spring2go.auth.domain;

import lombok.Data;

/**
 * 验证码
 *
 * @author xiaobin
 */
@Data
public class CaptchaModel {

    /**
     * key
     */
    private String key;

    /**
     * 验证码url
     */
    private String image;
}
