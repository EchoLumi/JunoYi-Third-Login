package com.junoyi.framework.captcha.generator;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.captcha.generator.MathGenerator;
import cn.hutool.core.util.IdUtil;
import com.junoyi.framework.captcha.domain.CaptchaResult;
import com.junoyi.framework.captcha.enums.CaptchaType;
import com.junoyi.framework.captcha.properties.CaptchaProperties;
import com.junoyi.framework.captcha.store.CaptchaStore;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * 图片验证码生成器
 *
 * @author Fan
 */
public class ImageCaptchaGenerator implements CaptchaGenerator {

    private final CaptchaProperties properties;
    private final CaptchaStore captchaStore;

    /**
     * 构造图片验证码生成器
     *
     * @param properties 验证码配置属性
     * @param captchaStore 验证码存储器
     */
    public ImageCaptchaGenerator(CaptchaProperties properties, CaptchaStore captchaStore) {
        this.properties = properties;
        this.captchaStore = captchaStore;
    }

    @Override
    public CaptchaType getType() {
        return CaptchaType.IMAGE;
    }

    /**
     * 生成图片验证码
     *
     * @return 验证码结果对象，包含验证码ID、类型、图片数据和过期时间
     */
    @Override
    public CaptchaResult generate() {
        CaptchaProperties.ImageCaptcha config = properties.getImage();
        String captchaId = IdUtil.fastSimpleUUID();
        String code;
        String imageBase64;

        if ("math".equalsIgnoreCase(config.getCodeType())) {
            // 数学运算验证码
            ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(
                    config.getWidth(), config.getHeight(), 0, 4);
            MathGenerator mathGenerator = new MathGenerator(1);
            captcha.setGenerator(mathGenerator);
            captcha.createCode();
            // 计算数学表达式结果
            code = calculateMathExpression(captcha.getCode());
            imageBase64 = captcha.getImageBase64Data();
        } else {
            // 字符验证码
            LineCaptcha captcha = CaptchaUtil.createLineCaptcha(
                    config.getWidth(), config.getHeight(), config.getLength(), config.getLineCount());
            captcha.createCode();
            code = captcha.getCode();
            imageBase64 = captcha.getImageBase64Data();
        }

        // 存储验证码
        captchaStore.save(captchaId, code, properties.getExpireSeconds());

        return new CaptchaResult()
                .setCaptchaId(captchaId)
                .setType(CaptchaType.IMAGE)
                .setImage(imageBase64)
                .setExpireSeconds(properties.getExpireSeconds());
    }

    /**
     * 验证图片验证码
     *
     * @param captchaId 验证码ID
     * @param params 验证码输入值
     * @return 验证结果，true表示验证通过，false表示验证失败
     */
    @Override
    public boolean validate(String captchaId, Object params) {
        if (params == null) return false;
        String inputCode = params.toString();
        return captchaStore.validateAndRemove(captchaId, inputCode);
    }

    /**
     * 计算数学表达式
     *
     * @param expression 数学表达式字符串
     * @return 计算结果字符串
     */
    private String calculateMathExpression(String expression) {
        try {
            // 移除等号
            String exp = expression.replace("=", "").trim();
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            if (engine != null) {
                Object result = engine.eval(exp);
                return String.valueOf(((Number) result).intValue());
            }
        } catch (Exception ignored) {
        }
        return expression;
    }
}
