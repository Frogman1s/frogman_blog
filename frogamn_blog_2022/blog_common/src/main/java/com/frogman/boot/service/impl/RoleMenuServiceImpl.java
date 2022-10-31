package com.frogman.boot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frogman.boot.domain.entity.RoleMenu;
import com.frogman.boot.service.RoleMenuService;
import com.frogman.boot.mapper.RoleMenuMapper;
import org.springframework.stereotype.Service;

/**
* @author allenshen
* @description 针对表【sys_role_menu(角色和菜单关联表)】的数据库操作Service实现
* @createDate 2022-10-28 13:12:11
*/
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu>
    implements RoleMenuService{

}




