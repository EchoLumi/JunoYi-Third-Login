package com.junoyi.system.controller;


import com.junoyi.framework.core.domain.base.BaseController;
import com.junoyi.framework.core.domain.module.R;
import com.junoyi.framework.security.annotation.PlatformScope;
import com.junoyi.framework.security.enums.PlatformType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/menu")
@RequiredArgsConstructor
public class SysMenuController extends BaseController {


    @GetMapping("/list")
    @PlatformScope(PlatformType.ADMIN_WEB)
    public R<?> getMenuList(){

        return R.ok();
    }

    @PostMapping()
    @PlatformScope(PlatformType.ADMIN_WEB)
    public R<?> addMenu(){
        return R.ok();
    }


    @PutMapping
    @PlatformScope(PlatformType.ADMIN_WEB)
    public R<?> updateMenu(){
        return R.ok();
    }
}
