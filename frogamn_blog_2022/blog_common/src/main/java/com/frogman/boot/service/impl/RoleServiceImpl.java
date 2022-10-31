package com.frogman.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frogman.boot.constants.SystemConstants;
import com.frogman.boot.domain.ResponseResult;
import com.frogman.boot.domain.entity.Role;
import com.frogman.boot.domain.vo.PageVo;
import com.frogman.boot.service.RoleService;
import com.frogman.boot.mapper.RoleMapper;
import com.frogman.boot.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
* @author allenshen
* @description 针对表【sys_role(角色信息表)】的数据库操作Service实现
* @createDate 2022-10-28 10:58:21
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleService roleService;
    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //判断是否是管理员，如果是返回集合里只有admin
        //不是管理员，查询当前用户的角色信息
        Long roleId = userRoleService.getById(id).getRoleId();
        String roleKey = roleService.getById(roleId).getRoleKey();
        ArrayList<String> list = new ArrayList<>();
        list.add(roleKey);
        return list;
    }

    @Override
    public ResponseResult listAllRole() {
        return ResponseResult.okResult(list());
    }

    @Override
    public ResponseResult getRoleList(Integer pageNum, Integer pageSize, String roleName, String status) {
        Page<Role> page = new Page<>(pageNum, pageSize);
        LambdaUpdateWrapper<Role> wrapper = new LambdaUpdateWrapper<Role>()
                .eq(StringUtils.hasText(status), Role::getStatus, status)
                .eq(StringUtils.hasText(roleName), Role::getRoleName, roleName);
        Page<Role> rolePage = page(page, wrapper);
        return ResponseResult.okResult(new PageVo(rolePage.getRecords(),rolePage.getTotal()));
    }
}




