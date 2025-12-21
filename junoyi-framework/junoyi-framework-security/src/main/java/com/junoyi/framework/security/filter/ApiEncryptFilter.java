package com.junoyi.framework.security.filter;

import com.junoyi.framework.core.utils.StringUtils;
import com.junoyi.framework.log.core.JunoYiLog;
import com.junoyi.framework.log.core.JunoYiLogFactory;
import com.junoyi.framework.security.properties.SecurityProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * API 加密过滤器
 * 用于对请求体和响应体进行加密/解密处理
 * 继承 OncePerRequestFilter 确保每个请求只执行一次过滤
 *
 * @author Fan
 */
@RequiredArgsConstructor
public class ApiEncryptFilter extends OncePerRequestFilter {

    private final JunoYiLog log = JunoYiLogFactory.getLogger(ApiEncryptFilter.class);

    private final SecurityProperties securityProperties;

    /**
     * 执行过滤逻辑
     *
     * @param request     HTTP 请求对象
     * @param response    HTTP 响应对象
     * @param filterChain 过滤器链
     * @throws ServletException Servlet 异常
     * @throws IOException      IO 异常
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 检查是否启用 API 加密
        if (!securityProperties.getApiEncrypt().isEnable()) {
            log.debug("ApiEncryptDisabled", "API 加密未启用，直接放行");
            filterChain.doFilter(request, response);
            return;
        }

        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        log.info("ApiEncryptFilter", "处理请求: " + method + " " + requestURI);

        try {
            // 包装请求和响应对象
            HttpServletRequest wrappedRequest = request;
            HttpServletResponse wrappedResponse = response;

            // 处理请求体解密（如果启用）
            if (securityProperties.getApiEncrypt().isRequest() && needDecryptRequest(request)) {
                wrappedRequest = decryptRequest(request);
                log.info("RequestDecrypted", "请求体已解密: " + requestURI);
            }

            // 处理响应体加密（如果启用）
            if (securityProperties.getApiEncrypt().isResponse() && needEncryptResponse(request)) {
                wrappedResponse = wrapResponseForEncryption(response);
                log.info("ResponseWrapped", "响应体将被加密: " + requestURI);
            }

            // 3. 继续执行过滤器链
            filterChain.doFilter(wrappedRequest, wrappedResponse);

            // 4. 执行响应体加密（在响应返回前）
            if (securityProperties.getApiEncrypt().isResponse() && needEncryptResponse(request)) {
                encryptResponse(wrappedResponse);
                log.info("ResponseEncrypted", "响应体已加密: " + requestURI);
            }

        } catch (Exception e) {
            log.error("ApiEncryptError", "API 加密处理失败: " + requestURI, e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":500,\"msg\":\"API 加密处理失败\"}");
        }
    }

    /**
     * 判断请求是否需要解密
     * 可以根据请求头、Content-Type、URL 等条件判断
     *
     * @param request HTTP 请求对象
     * @return true=需要解密，false=不需要解密
     */
    private boolean needDecryptRequest(HttpServletRequest request) {
        // TODO: 实现判断逻辑
        // 示例：检查请求头中是否有加密标识
        String encrypted = request.getHeader("X-Encrypted");
        if ("true".equalsIgnoreCase(encrypted)) {
            return true;
        }

        // 示例：只对 POST/PUT 请求进行解密
        String method = request.getMethod();
        if ("POST".equalsIgnoreCase(method) || "PUT".equalsIgnoreCase(method)) {
            // 检查 Content-Type 是否为 JSON
            String contentType = request.getContentType();
            if (contentType != null && contentType.contains("application/json")) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断响应是否需要加密
     * 可以根据请求头、Accept、URL 等条件判断
     *
     * @param request HTTP 请求对象
     * @return true=需要加密，false=不需要加密
     */
    private boolean needEncryptResponse(HttpServletRequest request) {
        // TODO: 实现判断逻辑
        // 示例：检查请求头中是否要求加密响应
        String needEncrypt = request.getHeader("X-Need-Encrypt");
        if ("true".equalsIgnoreCase(needEncrypt)) {
            return true;
        }

        // 示例：检查 Accept 头
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            return true;
        }

        return false;
    }

    /**
     * 解密请求体
     * 将加密的请求体解密为明文
     *
     * @param request 原始请求对象
     * @return 包装后的请求对象（包含解密后的数据）
     * @throws IOException IO 异常
     */
    private HttpServletRequest decryptRequest(HttpServletRequest request) throws IOException {
        // TODO: 实现请求体解密逻辑
        // 1. 读取请求体
        // 2. 解密数据
        // 3. 包装为新的 HttpServletRequest
        
        log.debug("DecryptRequest", "开始解密请求体");
        
        // 示例代码框架：
        // String encryptedBody = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
        // String decryptedBody = decryptData(encryptedBody);
        // return new DecryptedRequestWrapper(request, decryptedBody);
        
        return request; // 暂时返回原始请求
    }

    /**
     * 包装响应对象以便后续加密
     * 创建一个可以缓存响应内容的包装器
     *
     * @param response 原始响应对象
     * @return 包装后的响应对象
     */
    private HttpServletResponse wrapResponseForEncryption(HttpServletResponse response) {
        // TODO: 实现响应包装逻辑
        // 创建一个可以缓存响应内容的 HttpServletResponseWrapper
        
        log.debug("WrapResponse", "包装响应对象");
        
        // 示例代码框架：
        // return new EncryptedResponseWrapper(response);
        
        return response; // 暂时返回原始响应
    }

    /**
     * 加密响应体
     * 将明文响应体加密后写入响应
     *
     * @param response 响应对象
     * @throws IOException IO 异常
     */
    private void encryptResponse(HttpServletResponse response) throws IOException {
        // TODO: 实现响应体加密逻辑
        // 1. 从包装的响应对象中获取原始响应内容
        // 2. 加密数据
        // 3. 写入到实际的响应中
        
        log.debug("EncryptResponse", "开始加密响应体");
        
        // 示例代码框架：
        // if (response instanceof EncryptedResponseWrapper) {
        //     EncryptedResponseWrapper wrapper = (EncryptedResponseWrapper) response;
        //     String originalContent = wrapper.getCapturedContent();
        //     String encryptedContent = encryptData(originalContent);
        //     wrapper.writeEncryptedContent(encryptedContent);
        // }
    }

    /**
     * 加密数据（示例方法，需要实现具体的加密算法）
     *
     * @param data 明文数据
     * @return 加密后的数据
     */
    private String encryptData(String data) {
        // TODO: 实现具体的加密算法
        // 例如：AES、RSA、SM4 等
        log.debug("EncryptData", "加密数据，长度: " + (data != null ? data.length() : 0));
        return data;
    }

    /**
     * 解密数据（示例方法，需要实现具体的解密算法）
     *
     * @param encryptedData 加密的数据
     * @return 解密后的明文数据
     */
    private String decryptData(String encryptedData) {
        // TODO: 实现具体的解密算法
        // 例如：AES、RSA、SM4 等
        log.debug("DecryptData", "解密数据，长度: " + (encryptedData != null ? encryptedData.length() : 0));
        return encryptedData;
    }
}
