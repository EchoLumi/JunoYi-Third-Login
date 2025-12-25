package com.junoyi.system.service;

import com.junoyi.framework.captcha.domain.CaptchaResult;
import com.junoyi.framework.captcha.enums.CaptchaType;
import com.junoyi.framework.captcha.helper.CaptchaHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 验证码服务实现
 *
 * @author Fan
 */
@Service
@RequiredArgsConstructor
public class SysCaptchaServiceImpl implements ISysCaptchaService {

    private final CaptchaHelper captchaHelper;

    @Override
    public CaptchaResult getImageCaptcha() {
        return captchaHelper.generate(CaptchaType.IMAGE);
    }


    @Override
    public boolean validate(String captchaId, String code) {
        return captchaHelper.validate(captchaId, code);
    }

}
