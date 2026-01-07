package com.junoyi.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.junoyi.framework.core.domain.page.PageResult;
import com.junoyi.framework.core.utils.DateUtils;
import com.junoyi.framework.security.utils.SecurityUtils;
import com.junoyi.system.convert.SysPermissionConverter;
import com.junoyi.system.domain.dto.SysPermissionDTO;
import com.junoyi.system.domain.dto.SysPermissionQueryDTO;
import com.junoyi.system.domain.po.SysPermission;
import com.junoyi.system.domain.vo.SysPermissionVO;
import com.junoyi.system.mapper.SysPermissionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 权限池服务实现
 *
 * @author Fan
 */
@Service
@RequiredArgsConstructor
public class SysPermissionServiceImpl implements ISysPermissionService {

    private final SysPermissionMapper sysPermissionMapper;
    private final SysPermissionConverter sysPermissionConverter;

    @Override
    public PageResult<SysPermissionVO> getPermissionList(SysPermissionQueryDTO queryDTO, Page<SysPermission> page) {
        LambdaQueryWrapper<SysPermission> wrapper = new LambdaQueryWrapper<>();
        if (queryDTO != null) {
            wrapper.like(StringUtils.hasText(queryDTO.getPermission()), SysPermission::getPermission, queryDTO.getPermission())
                    .like(StringUtils.hasText(queryDTO.getDescription()), SysPermission::getDescription, queryDTO.getDescription())
                    .eq(queryDTO.getStatus() != null, SysPermission::getStatus, queryDTO.getStatus());
        }
        wrapper.orderByDesc(SysPermission::getCreateTime);

        Page<SysPermission> resultPage = sysPermissionMapper.selectPage(page, wrapper);
        List<SysPermissionVO> voList = sysPermissionConverter.toVoList(resultPage.getRecords());

        return PageResult.of(voList,
                resultPage.getTotal(),
                (int) resultPage.getCurrent(),
                (int) resultPage.getSize());
    }

    @Override
    public List<SysPermissionVO> getPermissionOptions() {
        LambdaQueryWrapper<SysPermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysPermission::getStatus, 1)
                .orderByAsc(SysPermission::getPermission);
        List<SysPermission> permissions = sysPermissionMapper.selectList(wrapper);
        return sysPermissionConverter.toVoList(permissions);
    }

    @Override
    public void addPermission(SysPermissionDTO dto) {
        SysPermission permission = sysPermissionConverter.toPo(dto);
        permission.setCreateBy(SecurityUtils.getUserName());
        permission.setCreateTime(DateUtils.getNowDate());
        sysPermissionMapper.insert(permission);
    }

    @Override
    public void updatePermission(SysPermissionDTO dto) {
        SysPermission permission = sysPermissionConverter.toPo(dto);
        permission.setUpdateBy(SecurityUtils.getUserName());
        permission.setUpdateTime(DateUtils.getNowDate());
        sysPermissionMapper.updateById(permission);
    }

    @Override
    public void deletePermission(Long id) {
        sysPermissionMapper.deleteById(id);
    }

    @Override
    public void deletePermissionBatch(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }
        sysPermissionMapper.deleteBatchIds(ids);
    }
}
