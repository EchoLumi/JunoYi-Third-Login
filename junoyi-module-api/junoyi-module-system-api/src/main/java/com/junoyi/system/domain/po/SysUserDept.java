package com.junoyi.system.domain.po;

import lombok.Data;

/**
 * 用户与部门关联数据实体
 *
 * @author Fan
 */
@Data
public class SysUserDept {

    private Long id;

    private Long userId;

    private Long deptId;
}