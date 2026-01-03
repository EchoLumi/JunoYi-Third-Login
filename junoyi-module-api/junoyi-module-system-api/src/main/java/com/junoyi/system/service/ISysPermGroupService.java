package com.junoyi.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.junoyi.framework.core.domain.page.PageResult;
import com.junoyi.system.domain.dto.SysPermGroupQueryDTO;
import com.junoyi.system.domain.po.SysPermGroup;
import com.junoyi.system.domain.vo.SysPermGroupVO;

import java.util.List;

/**
 * 权限组服务接口
 *
 * @author Fan
 */
public interface ISysPermGroupService {

    /**
     * 分页查询权限组列表
     *
     * @param queryDTO 查询条件
     * @param page 分页对象
     * @return 分页结果
     */
    PageResult<SysPermGroupVO> getPermGroupList(SysPermGroupQueryDTO queryDTO, Page<SysPermGroup> page);
}
