package com.frogman.boot.mapper;

import com.frogman.boot.domain.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author allenshen
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Mapper
* @createDate 2022-10-28 10:57:58
* @Entity com.frogman.boot.domain.entity.Menu
*/
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

}




