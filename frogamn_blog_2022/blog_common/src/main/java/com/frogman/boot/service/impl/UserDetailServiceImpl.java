package com.frogman.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.frogman.boot.domain.entity.LoginUser;
import com.frogman.boot.domain.entity.User;
import com.frogman.boot.domain.enums.AppHttpCodeEnum;
import com.frogman.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //根据用户名查询用户信息
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>().eq(User::getUserName, username);
        try {
            //查到 返回用户
            //TODO 查询权限信息封装
            User user = userService.getOne(wrapper);
            if (Objects.isNull(user)){
                throw new Exception(AppHttpCodeEnum.LOGIN_ERROR.getMsg());
            }
            return new LoginUser(user);
        }catch (Exception e){
            //没查到 抛出异常
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
