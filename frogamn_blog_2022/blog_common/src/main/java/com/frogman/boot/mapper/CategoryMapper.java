package com.frogman.boot.mapper;

import com.frogman.boot.domain.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author allenshen
* @description 针对表【fm_category(分类表)】的数据库操作Mapper
* @createDate 2022-10-21 23:04:58
* @Entity com.frogman.boot.domain.entity.Category
*/
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}




