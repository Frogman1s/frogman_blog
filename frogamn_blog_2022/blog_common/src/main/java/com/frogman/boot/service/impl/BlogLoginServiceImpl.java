package com.frogman.boot.service.impl;

import cn.hutool.core.convert.Convert;
import com.frogman.boot.domain.ResponseResult;
import com.frogman.boot.domain.entity.LoginUser;
import com.frogman.boot.domain.entity.User;
import com.frogman.boot.domain.enums.AppHttpCodeEnum;
import com.frogman.boot.domain.vo.BlogUserLoginVo;
import com.frogman.boot.domain.vo.UserInfoVo;
import com.frogman.boot.exception.SystemException;
import com.frogman.boot.service.BlogLoginService;
import com.frogman.boot.utils.JwtUtil;
import com.frogman.boot.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BlogLoginServiceImpl implements BlogLoginService {
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
        redisCache.setCacheObject("bloglogin:" + userId, loginUser);
        //token和userinfo封装
        UserInfoVo userInfoVo = Convert.convert(UserInfoVo.class, loginUser.getUser());
        BlogUserLoginVo blogUserLoginVo = new BlogUserLoginVo(token, userInfoVo);

        return ResponseResult.okResult(blogUserLoginVo);
        }

    @Override
    public ResponseResult logout() {
        //获取token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser)authentication.getPrincipal();
        //解析token 获取userid
        Long userId = loginUser.getUser().getId();
        //从redis删除user信息
        redisCache.deleteObject("bloglogin:"+userId);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

}
