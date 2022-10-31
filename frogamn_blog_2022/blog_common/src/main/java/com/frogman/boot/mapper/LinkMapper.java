package com.frogman.boot.mapper;

import com.frogman.boot.domain.entity.Link;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author allenshen
* @description 针对表【fm_link(友链)】的数据库操作Mapper
* @createDate 2022-10-23 19:24:38
* @Entity com.frogman.boot.domain.entity.Link
*/
@Mapper
public interface LinkMapper extends BaseMapper<Link> {

}




