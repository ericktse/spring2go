package com.spring2go.auth.service;

import com.spring2go.auth.domain.CaptchaModel;
import com.spring2go.common.core.domain.R;

import java.io.IOException;

/**
 * 验证码
 *
 * @author xiaobin
 */
public interface CaptchaService {

    /**
     * 生成验证码
     */
    CaptchaModel createCaptcha() throws IOException;

    /**
     * 校验验证码
     */
    Boolean checkCaptcha(String key, String captcha);
}
