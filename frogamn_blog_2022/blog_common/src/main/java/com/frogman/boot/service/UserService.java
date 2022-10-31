package com.frogman.boot.service;

import com.frogman.boot.domain.ResponseResult;
import com.frogman.boot.domain.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.frogman.boot.domain.vo.UserPutVo;

import java.util.List;

/**
* @author allenshen
* @description 针对表【sys_user(用户表)】的数据库操作Service
* @createDate 2022-10-23 20:04:25
*/
public interface UserService extends IService<User> {

    ResponseResult getUserInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    ResponseResult userList(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status);

    ResponseResult changeStatus(Long userId, String status);

    ResponseResult removeUserByIds(List<Long> ids);

    ResponseResult addUser(UserPutVo userPutVo);
}
