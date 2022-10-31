package com.frogman.boot.service;

import com.frogman.boot.domain.ResponseResult;
import com.frogman.boot.domain.dto.ArticleDto;
import com.frogman.boot.domain.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
* @author allenshen
* @description 针对表【fm_article(文章表)】的数据库操作Service
* @createDate 2022-10-21 16:36:21
*/
public interface ArticleService extends IService<Article> {

    ResponseResult hotArticleList();

    ResponseResult<Article> articleList(Long categoryId,Integer pageNum,Integer pageSize);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult addArticle(ArticleDto articleDto);

    ResponseResult getArticleList(Integer pageNum, Integer pageSize, String title, String summary);

    ResponseResult removeArticleByIds(List<Long> ids);

    ResponseResult getInfoById(Long id);

    ResponseResult updateArticleById(Article article);
}
