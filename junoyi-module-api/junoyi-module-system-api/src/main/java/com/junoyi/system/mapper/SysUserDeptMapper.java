package com.junoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.junoyi.framework.datasource.datascope.annotation.IgnoreDataScope;
import com.junoyi.system.domain.po.SysUserDept;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户部门关联 Mapper
 *
 * @author Fan
 */
@Mapper
@IgnoreDataScope
public interface SysUserDeptMapper extends BaseMapper<SysUserDept> {
}
