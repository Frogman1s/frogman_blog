package com.frogman.boot.controller;

import com.frogman.boot.annotation.SystemLog;
import com.frogman.boot.domain.ResponseResult;
import com.frogman.boot.domain.entity.User;
import com.frogman.boot.domain.enums.AppHttpCodeEnum;
import com.frogman.boot.exception.SystemException;
import com.frogman.boot.service.BlogLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


@RestController
public class BlogLoginController {

    @Autowired
    private BlogLoginService blogLoginService;

    @PostMapping("/login")
    @SystemLog(businessName = "用户登录")
    public ResponseResult login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
            //提示  需要输入用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return blogLoginService.login(user);
    }

    @PostMapping("/logout")
    public ResponseResult logout(){
        return blogLoginService.logout();
    }
}
