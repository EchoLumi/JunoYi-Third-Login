package com.junoyi.system.domain.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 系统信息Vo
 *
 * @author Fan
 */
@Data
@Builder
public class SystemInfoVo {
    private String name;
    private String version;
    private String copyrightYear;
    private String copyright;
    private String registration;
    private String logo;
}