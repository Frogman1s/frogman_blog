package com.frogman.boot.mapper;

import com.frogman.boot.domain.entity.ArticleTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author allenshen
* @description 针对表【fm_article_tag(文章标签关联表)】的数据库操作Mapper
* @createDate 2022-10-29 12:16:18
* @Entity com.frogman.boot.domain.entity.ArticleTag
*/
@Mapper
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

}




