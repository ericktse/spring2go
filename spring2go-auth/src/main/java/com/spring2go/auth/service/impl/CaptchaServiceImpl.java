package com.spring2go.auth.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.spring2go.auth.domain.CaptchaModel;
import com.spring2go.auth.service.CaptchaService;
import com.spring2go.common.core.constant.AuthConstants;
import com.spring2go.common.core.domain.R;
import com.spring2go.common.core.util.RandImageUtils;
import com.spring2go.common.core.util.StringUtils;
import com.spring2go.common.redis.util.RedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static org.bouncycastle.asn1.cmc.CMCStatus.failed;

/**
 * 验证码
 *
 * @author xiaobin
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CaptchaServiceImpl implements CaptchaService {

    private final RedisUtils redisUtils;
    private final String BASE_CHECK_CODES = "qwertyuiplkjhgfdsazxcvbnmQWERTYUPLKJHGFDSAZXCVBNM1234567890";

    @Override
    public CaptchaModel createCaptcha() throws IOException {

        //生成验证
        String code = RandomUtil.randomString(BASE_CHECK_CODES, 4);

        // 保存验证码信息
        String uuid = IdUtil.simpleUUID();
        String verifyKey = AuthConstants.CAPTCHA_CODE_KEY + uuid;

        //存到redis中
        redisUtils.setCacheObject(verifyKey, code, 60L, TimeUnit.SECONDS);
        log.info("获取验证码，Redis checkCode = {}，key = {}", code, verifyKey);

        //返回前端
        String base64 = RandImageUtils.generate(code);

        CaptchaModel captchaModel = new CaptchaModel();
        captchaModel.setKey(verifyKey);
        captchaModel.setImage(base64);

        return captchaModel;
    }

    @Override
    public Boolean checkCaptcha(String key, String captcha) {
        if (StringUtils.isEmpty(captcha)) {
            return false;
        }

        Object checkCode = redisUtils.getCacheObject(key);
        if (checkCode == null || !checkCode.toString().toLowerCase().equals(captcha.toLowerCase())) {
            log.warn("验证码错误，key= {} , Ui checkCode= {}, Redis checkCode = {}", key, captcha, checkCode);
            return false;

        }
        return true;
    }
}
