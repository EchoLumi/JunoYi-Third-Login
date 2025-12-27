package com.junoyi.system.controller;

import com.junoyi.framework.log.core.JunoYiLog;
import com.junoyi.framework.log.core.JunoYiLogFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 系统权限管理控制器
 * 提供权限相关的RESTful API接口，包括权限列表查询、权限详情获取、权限新增、修改和删除等操作
 *
 * @author Fan
 */
@RestController
@RequestMapping("/system/permission")
@RequiredArgsConstructor
public class SysPermissionController {

    private final JunoYiLog log = JunoYiLogFactory.getLogger(SysPermissionController.class);


}
