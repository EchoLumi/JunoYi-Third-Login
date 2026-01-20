package com.junoyi.system.controller;

import com.junoyi.framework.core.domain.module.R;
import com.junoyi.framework.log.core.JunoYiLog;
import com.junoyi.framework.log.core.JunoYiLogFactory;
import com.junoyi.framework.web.domain.BaseController;
import com.junoyi.system.service.ISysFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统文件上传控制类
 *
 * @author Fan
 */
@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
public class SysFileUploadController extends BaseController {

    private final JunoYiLog log = JunoYiLogFactory.getLogger(SysFileUploadController.class);

    private final ISysFileService sysFileService;

    /**
     * 上传文件
     */
    @PostMapping("/{bizType}")
    public R<Void> uploadAvatarImageFile(@PathVariable("bizType") String bizType){

        return R.ok();
    }

}