package com.frogman.boot.mapper;

import com.frogman.boot.domain.entity.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author allenshen
* @description 针对表【sys_user_role(用户和角色关联表)】的数据库操作Mapper
* @createDate 2022-10-28 13:01:54
* @Entity com.frogman.boot.domain.entity.UserRole
*/
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

}




