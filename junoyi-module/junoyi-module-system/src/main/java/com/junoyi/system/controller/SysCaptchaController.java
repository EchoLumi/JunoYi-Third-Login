package com.junoyi.system.controller;

import com.junoyi.framework.captcha.enums.CaptchaScene;
import com.junoyi.framework.captcha.enums.CaptchaType;
import com.junoyi.framework.core.domain.module.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Fan
 */
@RestController
@RequestMapping("/captcha")
@RequiredArgsConstructor
public class SysCaptchaController {

    /**
     * 获取验证码接口
     * 本接口根据传入场景，去动态判断是否用什么样的验证码
     * @param scene 验证场景
     * @param captchaType 验证类型
     * @return R<?> 统一响应结果
     */
    @GetMapping
    public R<?> getCaptcha(@RequestParam CaptchaScene scene, @RequestParam(required = false) CaptchaType captchaType){

        // 解析场景



        return R.ok();
    }

    @PostMapping("/send")
    public R<?> sendCaptchaMessage(){
        return R.ok();
    }

}