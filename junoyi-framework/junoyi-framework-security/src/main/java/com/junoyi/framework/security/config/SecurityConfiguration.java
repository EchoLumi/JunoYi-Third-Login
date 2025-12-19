package com.junoyi.framework.security.config;

import com.junoyi.framework.log.core.JunoYiLog;
import com.junoyi.framework.log.core.JunoYiLogFactory;
import com.junoyi.framework.security.filter.ApiEncryptFilter;
import com.junoyi.framework.security.filter.JwtAuthenticationTokenFilter;
import com.junoyi.framework.security.helper.TokenHelper;
import com.junoyi.framework.security.properties.SecurityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.util.AntPathMatcher;

/**
 * Security 配置类
 *
 * @author Fan
 */
@AutoConfiguration
@EnableConfigurationProperties(SecurityProperties.class)
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JunoYiLog log = JunoYiLogFactory.getLogger(SecurityConfiguration.class);
    
    private final TokenHelper tokenHelper;

    private final SecurityProperties securityProperties;

    /**
     * 注册 API 加密过滤器
     * 在 JWT 认证之前执行，用于解密请求和加密响应
     *
     * @return FilterRegistrationBean 过滤器注册对象
     */
    @Bean
    public FilterRegistrationBean<ApiEncryptFilter> apiEncryptFilter() {
        // 创建过滤器实例
        ApiEncryptFilter filter = new ApiEncryptFilter(securityProperties);
        
        FilterRegistrationBean<ApiEncryptFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(filter);
        // 拦截所有请求
        registrationBean.addUrlPatterns("/*");
        // 设置过滤器执行顺序（在 JWT 认证之前执行）
        registrationBean.setOrder(0);
        registrationBean.setName("apiEncryptFilter");
        
        log.info("FilterRegistrationBean","API encryption filter has been registered.");
        
        return registrationBean;
    }

    /**
     * 注册 JWT 认证过滤器
     * 设置过滤器的执行顺序和拦截路径
     *
     * @return FilterRegistrationBean 过滤器注册对象
     */
    @Bean
    public FilterRegistrationBean<JwtAuthenticationTokenFilter> jwtAuthenticationFilter() {
        // 创建过滤器实例
        JwtAuthenticationTokenFilter filter = new JwtAuthenticationTokenFilter(tokenHelper, securityProperties);
        
        FilterRegistrationBean<JwtAuthenticationTokenFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(filter);
        // 拦截所有请求
        registrationBean.addUrlPatterns("/*");
        // 设置过滤器执行顺序（在 API 加密之后执行）
        registrationBean.setOrder(1);
        registrationBean.setName("jwtAuthenticationTokenFilter");
        
        log.info("FilterRegistrationBean","JWT authentication filter has been registered.");
        
        return registrationBean;
    }

    /**
     * 注册路径匹配器 Bean
     * 用于白名单路径匹配
     *
     * @return AntPathMatcher 实例
     */
    @Bean
    public AntPathMatcher antPathMatcher() {
        return new AntPathMatcher();
    }
}
