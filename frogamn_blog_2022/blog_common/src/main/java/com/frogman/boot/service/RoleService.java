package com.frogman.boot.service;

import com.frogman.boot.domain.ResponseResult;
import com.frogman.boot.domain.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author allenshen
* @description 针对表【sys_role(角色信息表)】的数据库操作Service
* @createDate 2022-10-28 10:58:21
*/
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);

    ResponseResult listAllRole();

    ResponseResult getRoleList(Integer pageNum, Integer pageSize, String roleName, String status);
}
