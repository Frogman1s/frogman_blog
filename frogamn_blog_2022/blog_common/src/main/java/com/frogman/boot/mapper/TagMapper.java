package com.frogman.boot.mapper;

import com.frogman.boot.domain.entity.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author allenshen
* @description 针对表【fm_tag(标签)】的数据库操作Mapper
* @createDate 2022-10-27 21:28:30
* @Entity com.frogman.boot.domain.entity.Tag
*/
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

}




