package com.junoyi.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.junoyi.framework.core.domain.page.PageResult;
import com.junoyi.system.convert.SysPermGroupConverter;
import com.junoyi.system.domain.dto.SysPermGroupQueryDTO;
import com.junoyi.system.domain.po.SysPermGroup;
import com.junoyi.system.domain.vo.SysPermGroupVO;
import com.junoyi.system.mapper.SysPermGroupMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 权限组服务实现
 *
 * @author Fan
 */
@Service
@RequiredArgsConstructor
public class SysPermGroupServiceImpl implements ISysPermGroupService {

    private final SysPermGroupMapper sysPermGroupMapper;
    private final SysPermGroupConverter sysPermGroupConverter;

    @Override
    public PageResult<SysPermGroupVO> getPermGroupList(SysPermGroupQueryDTO queryDTO, Page<SysPermGroup> page) {
        LambdaQueryWrapper<SysPermGroup> wrapper = new LambdaQueryWrapper<>();
        if (queryDTO != null) {
            wrapper.like(StringUtils.hasText(queryDTO.getGroupCode()), SysPermGroup::getGroupCode, queryDTO.getGroupCode())
                    .like(StringUtils.hasText(queryDTO.getGroupName()), SysPermGroup::getGroupName, queryDTO.getGroupName())
                    .eq(queryDTO.getStatus() != null, SysPermGroup::getStatus, queryDTO.getStatus());
        }
        wrapper.orderByAsc(SysPermGroup::getPriority);

        Page<SysPermGroup> resultPage = sysPermGroupMapper.selectPage(page, wrapper);
        return PageResult.of(sysPermGroupConverter.toVoList(resultPage.getRecords()),
                resultPage.getTotal(),
                (int) resultPage.getCurrent(),
                (int) resultPage.getSize());
    }
}
