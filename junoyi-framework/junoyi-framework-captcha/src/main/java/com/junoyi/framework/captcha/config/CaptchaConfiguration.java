package com.junoyi.framework.captcha.config;

import com.junoyi.framework.captcha.generator.ImageCaptchaGenerator;
import com.junoyi.framework.captcha.generator.CaptchaGenerator;
import com.junoyi.framework.captcha.helper.CaptchaHelper;
import com.junoyi.framework.captcha.helper.CaptchaHelperImpl;
import com.junoyi.framework.captcha.properties.CaptchaProperties;
import com.junoyi.framework.captcha.store.CaptchaStore;
import com.junoyi.framework.captcha.store.RedisCaptchaStore;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 验证码模块自动配置
 *
 * @author Fan
 */
@Configuration
@EnableConfigurationProperties(CaptchaProperties.class)
@ConditionalOnProperty(prefix = "junoyi.captcha", name = "enable", havingValue = "true", matchIfMissing = true)
public class CaptchaConfiguration {

    private static final Logger log = LoggerFactory.getLogger(CaptchaConfiguration.class);

    /**
     * 验证码存储 - Redis实现
     */
    @Bean
    @ConditionalOnMissingBean(CaptchaStore.class)
    @ConditionalOnBean(RedissonClient.class)
    public CaptchaStore captchaStore(RedissonClient redissonClient) {
        log.info("[Captcha] Redis captcha store initialized");
        return new RedisCaptchaStore(redissonClient);
    }

    /**
     * 图片验证码生成器
     */
    @Bean
    @ConditionalOnBean(CaptchaStore.class)
    public ImageCaptchaGenerator imageCaptchaGenerator(CaptchaProperties properties, CaptchaStore captchaStore) {
        log.info("[Captcha] Image captcha generator initialized, code type: {}", properties.getImage().getCodeType());
        return new ImageCaptchaGenerator(properties, captchaStore);
    }

    /**
     * 验证码帮助类
     */
    @Bean
    @ConditionalOnMissingBean(CaptchaHelper.class)
    public CaptchaHelper captchaHelper(CaptchaProperties properties, List<CaptchaGenerator> generators) {
        log.info("[Captcha] CaptchaHelper initialized, default type: {}, available generators: {}",
                properties.getType(), generators.size());
        return new CaptchaHelperImpl(properties, generators);
    }
}
