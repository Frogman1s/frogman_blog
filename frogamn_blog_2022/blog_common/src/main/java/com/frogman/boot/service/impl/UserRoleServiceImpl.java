package com.frogman.boot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frogman.boot.domain.entity.UserRole;
import com.frogman.boot.service.UserRoleService;
import com.frogman.boot.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author allenshen
* @description 针对表【sys_user_role(用户和角色关联表)】的数据库操作Service实现
* @createDate 2022-10-28 13:01:54
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService{

}




