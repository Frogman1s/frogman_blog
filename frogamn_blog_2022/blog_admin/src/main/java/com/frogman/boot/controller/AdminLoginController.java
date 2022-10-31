package com.frogman.boot.controller;

import cn.hutool.core.convert.Convert;
import com.frogman.boot.annotation.SystemLog;
import com.frogman.boot.domain.ResponseResult;
import com.frogman.boot.domain.entity.LoginUser;
import com.frogman.boot.domain.entity.User;
import com.frogman.boot.domain.enums.AppHttpCodeEnum;
import com.frogman.boot.domain.vo.AdminUserInfoVo;
import com.frogman.boot.domain.vo.MenuVo;
import com.frogman.boot.domain.vo.RouterVo;
import com.frogman.boot.domain.vo.UserInfoVo;
import com.frogman.boot.exception.SystemException;
import com.frogman.boot.service.AdminLoginService;
import com.frogman.boot.service.BlogLoginService;
import com.frogman.boot.service.MenuService;
import com.frogman.boot.service.RoleService;
import com.frogman.boot.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping
public class AdminLoginController {

    @Autowired
    private AdminLoginService adminLoginService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;

    @PostMapping("/user/login")
    @SystemLog(businessName = "用户登录")
    public ResponseResult login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
            //提示  需要输入用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return adminLoginService.login(user);
    }

    @PostMapping("/user/logout")
    public ResponseResult logout(){
        return adminLoginService.logout();
    }

    @GetMapping("/getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo(){
        //获取当前登录用户的信息
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //根据用户id查询权限信息
        List<String> perms=menuService.selectPermsByUserId(loginUser.getUser().getId());
        //根据id查询角色信息
        List<String>roleKey=roleService.selectRoleKeyByUserId(loginUser.getUser().getId());
        //封装  返回
        UserInfoVo userInfoVo = Convert.convert(UserInfoVo.class, loginUser.getUser());
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms,roleKey,userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }

    @GetMapping("/getRouters")
    public ResponseResult<RouterVo> getRouters(){
        Long userId = SecurityUtils.getUserId();
        List<MenuVo> menuVos = menuService.selectRouterMenuTreeByUserId(userId);
        return ResponseResult.okResult(new RouterVo(menuVos));
    }
}
