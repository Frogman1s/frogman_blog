package com.frogman.boot.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frogman.boot.constants.SystemConstants;
import com.frogman.boot.domain.ResponseResult;
import com.frogman.boot.domain.entity.Menu;
import com.frogman.boot.domain.entity.Role;
import com.frogman.boot.domain.entity.RoleMenu;
import com.frogman.boot.domain.vo.MenuVo;
import com.frogman.boot.domain.vo.PageVo;
import com.frogman.boot.service.MenuService;
import com.frogman.boot.mapper.MenuMapper;
import com.frogman.boot.service.RoleMenuService;
import com.frogman.boot.service.UserRoleService;
import com.frogman.boot.utils.RouterMenuTreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @author allenshen
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Service实现
* @createDate 2022-10-28 10:57:58
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService{
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleMenuService roleMenuService;
    @Override
    public List<String> selectPermsByUserId(Long id) {

        //如果是管理员（id是1），返回菜单类型为C或F，状态为可用，未被删除的权限
        if(SystemConstants.SYS_ADMIN.equals(id)){
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<Menu>()
                    .eq(Menu::getDelFlag, SystemConstants.DELETED_FALSE_)
                    .eq(Menu::getStatus, SystemConstants.STATUS_NORMAL)
                    .in(Menu::getMenuType, SystemConstants.MENUTYPE_MENU,SystemConstants.MENUTYPE_BUTTON)
                    .select(Menu::getPerms);
            List<Menu> list = list(wrapper);
            List<String> perms = list.stream().map(menu -> menu.getPerms()).collect(Collectors.toList());
            return perms;
        }
        //否则返回其所对应的角色拥有的权限
        Long roleId = userRoleService.getById(id).getRoleId();
        List<Long> menuIds = roleMenuService.list(new LambdaQueryWrapper<RoleMenu>()
                        .eq(RoleMenu::getRoleId, roleId))
                        .stream()
                        .map(RoleMenu::getMenuId)
                        .collect(Collectors.toList());
        List<String> perms = new ArrayList<>();
        for (Long menuId : menuIds) {
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<Menu>()
                    .eq(Menu::getId, menuId);
            perms.add(getOne(wrapper).getPerms());
        }
        return perms;

    }

    @Override
    public List<MenuVo> selectRouterMenuTreeByUserId(Long userId) {
        List<Menu> menuList = null;
        //是管理员，返回所有符合要求的Menu,查menuType是M或C类型的
        if(SystemConstants.SYS_ADMIN.equals(userId)){
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<Menu>()
                    .eq(Menu::getDelFlag, SystemConstants.DELETED_FALSE_)
                    .eq(Menu::getStatus, SystemConstants.STATUS_NORMAL)
                    .in(Menu::getMenuType, SystemConstants.MENUTYPE_MENU, SystemConstants.MENUTYPE_CATEGORY)
                    .orderByAsc(Menu::getOrderNum);
            menuList = list(wrapper);
        }else {
            //不是管理员，查询用户对应的Menu查menuType是M或C类型的
            Long roleId = userRoleService.getById(userId).getRoleId();
            List<Long> menuIds = roleMenuService.list(new LambdaQueryWrapper<RoleMenu>()
                            .eq(RoleMenu::getRoleId, roleId))
                    .stream()
                    .map(RoleMenu::getMenuId)
                    .collect(Collectors.toList());
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<Menu>()
                    .eq(Menu::getDelFlag, SystemConstants.DELETED_FALSE_)
                    .eq(Menu::getStatus, SystemConstants.STATUS_NORMAL)
                    .in(Menu::getMenuType, SystemConstants.MENUTYPE_MENU, SystemConstants.MENUTYPE_CATEGORY)
                    .in(Menu::getId,menuIds)
                    .orderByAsc(Menu::getOrderNum);
            menuList = list(wrapper);
        }
        //Menu转MenuVo,生成树形结构
        List<MenuVo> menuVos = RouterMenuTreeUtil.routerMenuTreeHelper(menuList);
        return menuVos;
    }

    @Override
    public ResponseResult getMenuList(Integer pageNum, Integer pageSize, String menuName, String status) {
        LambdaUpdateWrapper<Menu> wrapper = new LambdaUpdateWrapper<Menu>()
                .eq(StringUtils.hasText(status), Menu::getStatus, status)
                .eq(StringUtils.hasText(menuName), Menu::getMenuName, menuName);
        List<Menu> menuList = list(wrapper);
        return ResponseResult.okResult(menuList);
    }
}




