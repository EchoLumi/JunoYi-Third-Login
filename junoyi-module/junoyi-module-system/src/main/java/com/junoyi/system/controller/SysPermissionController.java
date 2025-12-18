package com.junoyi.system.controller;

import com.junoyi.framework.core.domain.module.R;
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

    /**
     * 获取权限列表
     * 查询系统中所有的权限信息列表
     *
     * @return 权限列表数据响应结果
     */
    @GetMapping("/list")
    public R<?> getPermissionList(){

        return R.ok();
    }

    /**
     * 获取权限选项列表
     * 获取用于前端下拉选择的权限选项数据
     *
     * @return 权限选项数据响应结果
     */
    @GetMapping("/options")
    public R<?> getPermissionOptions(){
        return R.ok();
    }

    /**
     * 根据ID获取权限详情
     * 通过权限ID查询具体的权限详细信息
     *
     * @param id 权限ID
     * @return 权限详情数据响应结果
     */
    @GetMapping("/{id}")
    public R<?> getPermission(@PathVariable("id") Long id){

        return R.ok();
    }

    /**
     * 根据编码获取权限详情
     * 通过权限编码查询具体的权限详细信息
     *
     * @param code 权限编码
     * @return 权限详情数据响应结果
     */
    @GetMapping("/code/{code}")
    public R<?> getPermission(@PathVariable("code") String code){
        return R.ok();
    }

    /**
     * 新增权限
     * 添加新的权限信息到系统中
     *
     * @return 新增结果响应
     */
    @PostMapping
    public R<?> addPermission(){
        return R.ok();
    }

    /**
     * 更新权限
     * 修改系统中已存在的权限信息
     *
     * @return 更新结果响应
     */
    @PutMapping
    public R<?> updatePermission(){
        return R.ok();
    }

    /**
     * 删除权限
     * 根据权限ID删除系统中的权限信息
     *
     * @param id 要删除的权限ID
     * @return 删除结果响应
     */
    @DeleteMapping("/{id}")
    public R<?> deletePermission(@PathVariable("id") Long id){
        return R.ok();
    }
}
