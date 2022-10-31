package com.frogman.boot.service.impl;

import com.frogman.boot.domain.ResponseResult;
import com.frogman.boot.domain.entity.LoginUser;
import com.frogman.boot.domain.entity.User;
import com.frogman.boot.domain.enums.AppHttpCodeEnum;
import com.frogman.boot.service.AdminLoginService;
import com.frogman.boot.utils.JwtUtil;
import com.frogman.boot.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class AdminLoginServiceImpl implements AdminLoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;
    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authentication)) {
            throw new RuntimeException();
        }
        //获取userid 生成token
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        String token = JwtUtil.createJWT(String.valueOf(userId));
        //用户信息存入redis
        redisCache.setCacheObject("adminlogin:" + userId, loginUser);
        //token和userinfo封装
        Map<String, String> map = new HashMap<>();
        map.put("token",token);

        return ResponseResult.okResult(map);
    }

    @Override
    public ResponseResult logout() {
        //获取token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser)authentication.getPrincipal();
        //解析token 获取userid
        Long userId = loginUser.getUser().getId();
        //从redis删除user信息
        redisCache.deleteObject("adminlogin:"+userId);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

}
