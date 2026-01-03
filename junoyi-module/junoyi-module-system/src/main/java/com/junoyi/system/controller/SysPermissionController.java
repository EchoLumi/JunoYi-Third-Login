package com.junoyi.system.controller;

import com.junoyi.framework.core.domain.module.R;
import com.junoyi.framework.log.core.JunoYiLog;
import com.junoyi.framework.log.core.JunoYiLogFactory;
import com.junoyi.framework.permission.annotation.Permission;
import com.junoyi.framework.security.annotation.PlatformScope;
import com.junoyi.framework.security.enums.PlatformType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 系统权限管理控制器
 *
 * @author Fan
 */
@RestController
@RequestMapping("/system/permission")
@RequiredArgsConstructor
public class SysPermissionController {

    private final JunoYiLog log = JunoYiLogFactory.getLogger(SysPermissionController.class);

    /**
     * 获取权限组列表
     */
    @GetMapping("/list")
    @PlatformScope(PlatformType.ADMIN_WEB)
    @Permission(
            value = {"system.ui.permission.view", "system.api.permission.get"}
    )
    public R<?> getPermissionGroupList(){

        return R.ok();
    }

    /**
     * 添加权限组
     */
    @PostMapping
    @PlatformScope(PlatformType.ADMIN_WEB)
    @Permission(
            value = {"system.ui.permission.view", "system.api.permission.add"}
    )
    public R<Void> addPermission(){
        return R.ok();
    }

    /**
     * 更新权限组
     */
    @PutMapping
    @PlatformScope(PlatformType.ADMIN_WEB)
    @Permission(
            value = {"system.ui.permission.view", "system.api.permission.update"}
    )
    public R<Void> updatePermission(){
        return R.ok();
    }

    /**
     * 删除权限组
     */
    @DeleteMapping("/{id}")
    @PlatformScope(PlatformType.ADMIN_WEB)
    @Permission(
            value = {"system.ui.permission.view", "system.api.permission.delete"}
    )
    public R<Void> deletePermission(@PathVariable("id") Long id){
        return R.ok();
    }

    /**
     * 批量删除权限组
     */
    @DeleteMapping("/{id}/batch")
    @PlatformScope(PlatformType.ADMIN_WEB)
    @Permission(
            value = {"system.ui.permission.view", "system.api.permission.delete"}
    )
    public R<Void> deletePermissionBatch(){
        return R.ok();
    }
}
