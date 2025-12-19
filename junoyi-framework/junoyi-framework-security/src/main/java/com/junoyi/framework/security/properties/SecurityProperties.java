package com.junoyi.framework.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

/**
 * 安全配置属性类，用于加载和管理安全相关的配置信息
 * 该类通过@ConfigurationProperties注解自动绑定application.yml中前缀为"junoyi.security"的配置项
 *
 * @author Fan
 */
@Data
@ConfigurationProperties(prefix = "junoyi.security")
public class SecurityProperties {

    /**
     * API加密配置信息
     */
    private ApiEncrypt apiEncrypt;

    /**
     * 白名单URL列表，用于配置不需要安全验证的接口路径
     */
    private List<String> whitelist;

    /**
     * Token配置信息，包括令牌相关参数设置
     */
    private Token token;

    /**
     * API加密配置内部类，用于管理API请求和响应的加密设置
     */
    @Data
    public static class ApiEncrypt {
        /**
         * 是否启用API加密功能
         */
        private boolean enable;

        /**
         * 是否对请求数据进行加密
         */
        private boolean request;

        /**
         * 是否对响应数据进行加密
         */
        private boolean response;
    }

    /**
     * Token配置内部类，用于管理令牌相关的配置参数
     */
    @Data
    public static class Token {
        /**
         * Token在HTTP请求头中的字段名称
         */
        private String header;

        /**
         * Token签名密钥，用于生成和验证令牌
         */
        private String secret;

        /**
         * 访问令牌过期时间配置，key为用户类型，value为过期时间
         */
        private Map<String, String> accessExpire;

        /**
         * 刷新令牌过期时间配置，key为用户类型，value为过期时间
         */
        private Map<String, String> refreshExpire;
    }
}
