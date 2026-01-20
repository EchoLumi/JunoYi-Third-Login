package com.junoyi.demo.controller;

import com.junoyi.demo.domain.UserInfoVO;
import com.junoyi.demo.event.TestEvent;
import com.junoyi.framework.core.domain.module.R;
import com.junoyi.framework.datasource.datascope.DataScopeContextHolder;
import com.junoyi.framework.datasource.datascope.DataScopeContextHolder.DataScopeContext;
import com.junoyi.framework.event.core.EventBus;
import com.junoyi.framework.file.domain.FileInfo;
import com.junoyi.framework.file.helper.FileHelper;
import com.junoyi.framework.log.core.JunoYiLog;
import com.junoyi.framework.log.core.JunoYiLogFactory;
import com.junoyi.framework.permission.annotation.Permission;
import com.junoyi.framework.security.annotation.PlatformScope;
import com.junoyi.framework.security.enums.PlatformType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/demo")
@RequiredArgsConstructor
public class TestController {

    private final JunoYiLog log = JunoYiLogFactory.getLogger(TestController.class);
    private final FileHelper fileHelper;

    @GetMapping("/event")
    public void testEvent(){
        EventBus.get().callEvent(new TestEvent("测试事件"));
    }

    /**
     * 只允许 网页端使用 (后台管理web、 前台用户web)
     */
    @PlatformScope( {PlatformType.ADMIN_WEB, PlatformType.FRONT_DESK_WEB})
    @GetMapping("/web")
    public R<String> helloWorld(){
        return R.ok("Hello World");
    }

    /**
     * 只允许小程序和APP使用
     */
    @PlatformScope( {PlatformType.MINI_PROGRAM, PlatformType.APP} )
    @GetMapping("/app")
    public R<String> helloWorldApp(){
        return R.ok();
    }

    /**
     * 只允许桌面端使用
     */
    @PlatformScope( {PlatformType.DESKTOP_APP, PlatformType.ADMIN_WEB} )
    @GetMapping("/desktop")
    public R<String> helloWorldDesktop() {
        return R.ok();
    }

    @GetMapping("/xss")
    public R<?> testXss(){
        return R.ok();
    }

    @PostMapping("/xss")
    public R<?> testXss2(@RequestParam Long id){
        return R.ok();
    }

    @GetMapping("/permission")
    @Permission("system.demo.permission")
    @PlatformScope(PlatformType.APP)
    public R<String> testPermission(){
        return R.ok("Hello World");
    }

    /**
     * 测试字段权限
     * <p>
     * 根据用户权限返回不同的字段值：
     * - 有 field.user.xxx 权限：显示完整值
     * - 无权限但配置了脱敏：显示脱敏值
     * - 无权限且未配置脱敏：显示 null
     */
    @GetMapping("/field-permission")
    public R<UserInfoVO> testFieldPermission() {
        UserInfoVO user = new UserInfoVO();
        user.setId(1L);
        user.setUsername("zhangsan");
        user.setNickName("张三");
        user.setPhone("13812345678");
        user.setIdCard("110101199001011234");
        user.setEmail("zhangsan@example.com");
        user.setSalary(new BigDecimal("15000.00"));
        user.setBankCard("6222021234567890123");
        user.setAddress("北京市朝阳区建国路100号");
        return R.ok(user);
    }

    /**
     * 测试数据范围 - 查看当前用户的数据范围信息
     * <p>
     * 返回当前登录用户的数据范围配置，包括：
     * - scopeType: 数据范围类型 (ALL/DEPT/DEPT_AND_CHILD/SELF)
     * - userId: 当前用户ID
     * - deptIds: 用户所属部门
     * - accessibleDeptIds: 可访问的部门ID列表
     * - superAdmin: 是否超级管理员
     */
    @GetMapping("/data-scope")
    public R<Map<String, Object>> testDataScope() {
        DataScopeContext context = DataScopeContextHolder.get();
        
        Map<String, Object> result = new HashMap<>();
        if (context == null) {
            result.put("message", "未登录或数据范围上下文为空");
            return R.ok(result);
        }
        
        result.put("scopeType", context.getScopeType() != null ? context.getScopeType().name() : null);
        result.put("scopeTypeDesc", context.getScopeType() != null ? context.getScopeType().getDesc() : null);
        result.put("userId", context.getUserId());
        result.put("userName", context.getUserName());
        result.put("deptIds", context.getDeptIds());
        result.put("accessibleDeptIds", context.getAccessibleDeptIds());
        result.put("superAdmin", context.isSuperAdmin());
        
        // 说明
        if (context.isSuperAdmin()) {
            result.put("说明", "超级管理员，可查看所有数据");
        } else if (context.getScopeType() != null) {
            switch (context.getScopeType()) {
                case ALL:
                    result.put("说明", "全部数据权限，可查看所有数据");
                    break;
                case DEPT:
                    result.put("说明", "本部门数据权限，只能查看部门ID在 " + context.getDeptIds() + " 中的数据");
                    break;
                case DEPT_AND_CHILD:
                    result.put("说明", "本部门及下级数据权限，只能查看部门ID在 " + context.getAccessibleDeptIds() + " 中的数据");
                    break;
                case SELF:
                    result.put("说明", "仅本人数据权限，只能查看 create_by = '" + context.getUserName() + "' 的数据");
                    break;
            }
        }
        
        return R.ok(result);
    }

    /**
     * 方式1：策略上传 - 带业务类型验证
     * <p>
     * 根据业务类型自动应用对应的上传策略，包括：
     * - 文件类型验证（只允许特定类型）
     * - 文件大小限制（不同业务类型有不同限制）
     * - 自动路径分类（存储到对应业务目录）
     * <p>
     * 适用场景：需要严格控制文件类型和大小的业务场景
     * 
     * @param file 上传的文件
     * @param businessType 业务类型：avatar(头像)、document(文档)、image(图片)、video(视频)、audio(音频)、other(其他)
     * @return 文件信息
     */
    @PostMapping("/upload-strategy")
    public R<FileInfo> uploadWithStrategy(@RequestParam("file") MultipartFile file,
                                          @RequestParam("businessType") String businessType) {
        try {
            log.info("Demo", "【策略上传】开始上传文件: {}, 业务类型: {}", file.getOriginalFilename(), businessType);
            
            // 使用策略上传，会进行文件类型和大小验证
            FileInfo fileInfo = fileHelper.uploadWithStrategy(file, businessType);
            
            log.info("Demo", "【策略上传】文件上传成功: {}", fileInfo.getFileUrl());
            return R.ok(fileInfo);
        } catch (IllegalArgumentException e) {
            log.warn("Demo", "【策略上传】文件验证失败: {}", e.getMessage());
            return R.fail(e.getMessage());
        } catch (Exception e) {
            log.error("Demo", "【策略上传】文件上传失败: {}", e.getMessage());
            return R.fail("文件上传失败: " + e.getMessage());
        }
    }

    /**
     * 方式2：智能检测上传 - 自动识别文件类型
     * <p>
     * 根据文件的 MIME 类型和扩展名自动检测业务类型，并存储到对应目录：
     * - 图片文件 → image/ 目录
     * - 视频文件 → video/ 目录
     * - 音频文件 → audio/ 目录
     * - 文档文件 → document/ 目录
     * - 其他文件 → other/ 目录
     * <p>
     * 适用场景：不需要严格验证，但希望自动分类存储的场景
     * 
     * @param file 上传的文件
     * @return 文件信息
     */
    @PostMapping("/upload-auto")
    public R<FileInfo> uploadWithAutoDetect(@RequestParam("file") MultipartFile file) {
        try {
            log.info("Demo", "【智能检测上传】开始上传文件: {}, 大小: {} bytes", file.getOriginalFilename(), file.getSize());
            
            // 不传路径参数，会自动根据文件类型检测并分类存储
            FileInfo fileInfo = fileHelper.upload(file);
            
            log.info("Demo", "【智能检测上传】文件上传成功: {}", fileInfo.getFileUrl());
            return R.ok(fileInfo);
        } catch (Exception e) {
            log.error("Demo", "【智能检测上传】文件上传失败: {}", e.getMessage());
            return R.fail("文件上传失败: " + e.getMessage());
        }
    }

    /**
     * 方式3：手动指定路径上传 - 完全自定义
     * <p>
     * 手动指定文件存储的业务类型路径，不进行任何验证和自动检测。
     * 最终存储路径格式：指定路径/yyyy/MM/dd/文件名
     * <p>
     * 适用场景：需要完全自定义存储路径的场景
     * 
     * @param file 上传的文件
     * @param path 存储路径（业务类型），如：avatar、document、image、custom-path 等
     * @return 文件信息
     */
    @PostMapping("/upload-manual")
    public R<FileInfo> uploadWithManualPath(@RequestParam("file") MultipartFile file,
                                            @RequestParam("path") String path) {
        try {
            log.info("Demo", "【手动路径上传】开始上传文件: {}, 指定路径: {}", file.getOriginalFilename(), path);
            
            // 手动指定路径，不进行验证和检测
            FileInfo fileInfo = fileHelper.upload(file, path);
            
            log.info("Demo", "【手动路径上传】文件上传成功: {}", fileInfo.getFileUrl());
            return R.ok(fileInfo);
        } catch (Exception e) {
            log.error("Demo", "【手动路径上传】文件上传失败: {}", e.getMessage());
            return R.fail("文件上传失败: " + e.getMessage());
        }
    }

    /**
     * 测试获取文件访问URL
     * <p>
     * 根据文件路径获取可访问的URL
     * 
     * @param filePath 文件存储路径
     * @param expireSeconds 可选的过期时间（秒），用于生成临时访问链接（仅OSS支持）
     * @return 文件访问URL
     */
    @GetMapping("/file-url")
    public R<Map<String, String>> testGetFileUrl(@RequestParam("filePath") String filePath,
                                                   @RequestParam(value = "expireSeconds", required = false) Long expireSeconds) {
        try {
            String fileUrl;
            if (expireSeconds != null && expireSeconds > 0) {
                fileUrl = fileHelper.getFileUrl(filePath, expireSeconds);
            } else {
                fileUrl = fileHelper.getFileUrl(filePath);
            }
            
            Map<String, String> result = new HashMap<>();
            result.put("filePath", filePath);
            result.put("fileUrl", fileUrl);
            if (expireSeconds != null) {
                result.put("expireSeconds", String.valueOf(expireSeconds));
            }
            
            return R.ok(result);
        } catch (Exception e) {
            log.error("Demo", "获取文件URL失败: {}", e.getMessage());
            return R.fail("获取文件URL失败: " + e.getMessage());
        }
    }

    /**
     * 测试文件下载
     * <p>
     * 下载指定路径的文件内容
     * 
     * @param filePath 文件存储路径
     * @return 文件字节数组和文件信息
     */
    @GetMapping("/download")
    public R<Map<String, Object>> testDownload(@RequestParam("filePath") String filePath) {
        try {
            byte[] fileData = fileHelper.download(filePath);
            
            Map<String, Object> result = new HashMap<>();
            result.put("filePath", filePath);
            result.put("fileSize", fileData.length);
            result.put("message", "文件下载成功，大小: " + fileData.length + " bytes");
            
            return R.ok(result);
        } catch (Exception e) {
            log.error("Demo", "文件下载失败: {}", e.getMessage());
            return R.fail("文件下载失败: " + e.getMessage());
        }
    }

    /**
     * 测试文件删除
     * <p>
     * 删除指定路径的文件
     * 
     * @param filePath 文件存储路径
     * @return 删除结果
     */
    @DeleteMapping("/file")
    public R<String> testDelete(@RequestParam("filePath") String filePath) {
        try {
            boolean success = fileHelper.delete(filePath);
            if (success) {
                return R.ok("文件删除成功");
            } else {
                return R.fail("文件删除失败");
            }
        } catch (Exception e) {
            log.error("Demo", "文件删除失败: {}", e.getMessage());
            return R.fail("文件删除失败: " + e.getMessage());
        }
    }

    /**
     * 测试文件是否存在
     * <p>
     * 检查指定路径的文件是否存在
     * 
     * @param filePath 文件存储路径
     * @return 文件是否存在
     */
    @GetMapping("/file-exists")
    public R<Map<String, Object>> testExists(@RequestParam("filePath") String filePath) {
        try {
            boolean exists = fileHelper.exists(filePath);
            
            Map<String, Object> result = new HashMap<>();
            result.put("filePath", filePath);
            result.put("exists", exists);
            result.put("message", exists ? "文件存在" : "文件不存在");
            
            return R.ok(result);
        } catch (Exception e) {
            log.error("Demo", "检查文件失败: {}", e.getMessage());
            return R.fail("检查文件失败: " + e.getMessage());
        }
    }
}