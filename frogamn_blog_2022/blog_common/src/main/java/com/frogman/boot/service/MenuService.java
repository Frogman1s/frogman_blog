package com.frogman.boot.service;

import com.frogman.boot.domain.ResponseResult;
import com.frogman.boot.domain.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.frogman.boot.domain.vo.MenuVo;

import java.util.List;

/**
* @author allenshen
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Service
* @createDate 2022-10-28 10:57:58
*/
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<MenuVo> selectRouterMenuTreeByUserId(Long userId);

    ResponseResult getMenuList(Integer pageNum, Integer pageSize, String menuName, String status);
}
