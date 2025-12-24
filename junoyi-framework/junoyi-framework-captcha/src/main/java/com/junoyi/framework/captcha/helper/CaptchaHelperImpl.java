package com.junoyi.framework.captcha.helper;

import com.junoyi.framework.captcha.domain.CaptchaResult;
import com.junoyi.framework.captcha.enums.CaptchaType;
import com.junoyi.framework.captcha.generator.CaptchaGenerator;
import com.junoyi.framework.captcha.properties.CaptchaProperties;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 验证码帮助类实现
 *
 * @author Fan
 */
public class CaptchaHelperImpl implements CaptchaHelper {

    private final CaptchaProperties properties;
    private final Map<CaptchaType, CaptchaGenerator> generatorMap;

    public CaptchaHelperImpl(CaptchaProperties properties, List<CaptchaGenerator> generators) {
        this.properties = properties;
        this.generatorMap = generators.stream()
                .collect(Collectors.toMap(CaptchaGenerator::getType, Function.identity()));
    }

    @Override
    public CaptchaResult generate() {
        return generate(properties.getType());
    }

    @Override
    public CaptchaResult generate(CaptchaType type) {
        CaptchaGenerator generator = getGenerator(type);
        return generator.generate();
    }

    @Override
    public boolean validate(String captchaId, String code) {
        if (captchaId == null || code == null) return false;
        // 默认使用图片验证码生成器验证
        CaptchaGenerator generator = getGenerator(CaptchaType.IMAGE);
        return generator.validate(captchaId, code);
    }

    private CaptchaGenerator getGenerator(CaptchaType type) {
        CaptchaGenerator generator = generatorMap.get(type);
        if (generator == null) {
            throw new IllegalArgumentException("Unsupported captcha type: " + type);
        }
        return generator;
    }
}
