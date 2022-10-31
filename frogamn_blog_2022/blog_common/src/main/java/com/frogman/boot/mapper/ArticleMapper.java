package com.frogman.boot.mapper;

import com.frogman.boot.domain.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author allenshen
* @description 针对表【fm_article(文章表)】的数据库操作Mapper
* @createDate 2022-10-21 16:36:21
* @Entity com.frogman.boot.domain.entity.Article
*/
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

}




