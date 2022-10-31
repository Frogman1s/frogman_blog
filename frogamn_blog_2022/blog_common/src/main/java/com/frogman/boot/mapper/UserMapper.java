package com.frogman.boot.mapper;

import com.frogman.boot.domain.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author allenshen
* @description 针对表【sys_user(用户表)】的数据库操作Mapper
* @createDate 2022-10-23 20:04:25
* @Entity com.frogman.boot.domain.entity.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




