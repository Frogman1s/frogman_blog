package com.frogman.boot.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frogman.boot.domain.ResponseResult;
import com.frogman.boot.domain.entity.LoginUser;
import com.frogman.boot.domain.entity.User;
import com.frogman.boot.domain.enums.AppHttpCodeEnum;
import com.frogman.boot.domain.vo.PageVo;
import com.frogman.boot.domain.vo.UserInfoVo;
import com.frogman.boot.domain.vo.UserPutVo;
import com.frogman.boot.domain.vo.UserVo;
import com.frogman.boot.exception.SystemException;
import com.frogman.boot.service.UserService;
import com.frogman.boot.mapper.UserMapper;
import com.frogman.boot.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Objects;

/**
* @author allenshen
* @description 针对表【sys_user(用户表)】的数据库操作Service实现
* @createDate 2022-10-23 20:04:25
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseResult getUserInfo() {
        //获取userId
        Long userId = SecurityUtils.getUserId();
        //查询user
        User user = getById(userId);
        //封装UserInfoVo
        UserInfoVo userInfoVo = Convert.convert(UserInfoVo.class, user);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        Long userId = SecurityUtils.getUserId();
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<User>()
                .set(User::getAvatar, user.getAvatar())
                .set(User::getEmail, user.getEmail())
                .set(User::getNickName, user.getNickName())
                .set(User::getSex, user.getSex())
                .eq(User::getId, userId);
        update(user,wrapper);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult register(User user) {
        //非空判断
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }else if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_PASSWORD);
        }else if(!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_NICKNAME);
        }else if(!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_EMAIL);
        }
        //user进行重复判断
        LambdaUpdateWrapper<User> wrapper1 = new LambdaUpdateWrapper<User>().eq(User::getUserName, user.getUserName());
        if(!Objects.isNull(getOne(wrapper1))){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        LambdaUpdateWrapper<User> wrapper2 = new LambdaUpdateWrapper<User>().eq(User::getEmail, user.getEmail());
        if(!Objects.isNull(getOne(wrapper2))){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        //加密，存储
        String pwd = passwordEncoder.encode(user.getPassword());
        user.setPassword(pwd);
        save(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult userList(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status) {
        Page<User> page = new Page<>(pageNum, pageSize);
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<User>()
                .eq(User::getDelFlag, 0)
                .like(StringUtils.hasText(userName), User::getUserName, userName)
                .like(StringUtils.hasText(phonenumber), User::getPhonenumber, phonenumber)
                .like(StringUtils.hasText(status), User::getStatus, status);
        Page<User> userPage = page(page, wrapper);
        PageVo vo = new PageVo(Convert.toList(UserVo.class, userPage.getRecords()), userPage.getTotal());
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult changeStatus(Long userId, String status) {
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<User>()
                .eq(User::getId, userId)
                .set(User::getStatus, status);
        update(wrapper);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult removeUserByIds(List<Long> ids) {
        for (Long id : ids) {
            LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<User>()
                    .eq(User::getId, id)
                    .set(User::getDelFlag, 1);
            update(wrapper);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult addUser(UserPutVo userPutVo) {
        User user = Convert.convert(User.class, userPutVo);
        saveOrUpdate(user);
        return ResponseResult.okResult();
    }

}




