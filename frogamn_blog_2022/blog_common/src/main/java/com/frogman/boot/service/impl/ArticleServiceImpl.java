package com.frogman.boot.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frogman.boot.constants.SystemConstants;
import com.frogman.boot.domain.ResponseResult;
import com.frogman.boot.domain.dto.ArticleDto;
import com.frogman.boot.domain.entity.Article;
import com.frogman.boot.domain.vo.ArticleDetailVo;
import com.frogman.boot.domain.vo.ArticleListVo;
import com.frogman.boot.domain.vo.HotArticleVo;
import com.frogman.boot.domain.vo.PageVo;
import com.frogman.boot.service.ArticleService;
import com.frogman.boot.mapper.ArticleMapper;
import com.frogman.boot.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* @author allenshen
* @description 针对表【fm_article(文章表)】的数据库操作Service实现
* @createDate 2022-10-21 16:36:21
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService{

    @Autowired
    private CategoryServiceImpl categoryService;
    @Autowired
    private RedisCache redisCache;
    @Override
    public ResponseResult hotArticleList() {
        /***
         * 正式文章
         * 浏览量降序
         * 分页查询（10条/页）
         */
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<Article>()
                .eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL)
                .orderByDesc(Article::getViewCount);
        Page<Article> page = new Page<>(1,10);
        ArrayList<HotArticleVo> vos = new ArrayList<>();
        List<Article> records = page(page, queryWrapper).getRecords();
        List<HotArticleVo> hotArticleVos = Convert.toList(HotArticleVo.class, records);
        return ResponseResult.okResult(hotArticleVos);
    }

    @Override
    public ResponseResult<Article> articleList(Long categoryId,Integer pageNum,Integer pageSize) {


        //查询条件
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<Article>()
                .eq(Objects.nonNull(categoryId)&&categoryId>0,Article::getCategoryId,categoryId)//传入了categoryId，进行categoryId筛选，没传入就不筛选
                .eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL)//文章状态筛选
                .orderByDesc(Article::getIsTop);//按照是否置顶倒序排序
        //分页查询
        Page<Article> page = new Page<>(pageNum, pageSize);
        List<Article> articles = page(page, wrapper).getRecords();
        //查询categoryName
        articles = articles.stream()
                .map(article -> {
                    String name = categoryService.getById(article.getCategoryId()).getName();
                    article.setCategoryName(name);
                    return article;
                })
                .collect(Collectors.toList());
        //封装查询结果
        List<ArticleListVo> articleListVos = Convert.toList(ArticleListVo.class, articles);
        PageVo pageVo = new PageVo(articleListVos, (long) articleListVos.size());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询文章
        Article article = getById(id);
        //根据分类id查询
        Long categoryId = article.getCategoryId();
        article.setCategoryName(String.valueOf(categoryService.getById(categoryId)));
        //转成vo
        ArticleDetailVo articleDetailVo = Convert.convert(ArticleDetailVo.class, article);
        //封装
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        //更新redis中浏览量
        redisCache.incrementCacheMapValue(SystemConstants.VIEW_COUNT,id.toString(),1);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult addArticle(ArticleDto articleDto) {
        Article article = Convert.convert(Article.class, articleDto);
        save(article);
        return null;
    }

    @Override
    public ResponseResult getArticleList(Integer pageNum, Integer pageSize, String title, String summary) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<Article>()
                .like(StringUtils.hasText(title),Article::getTitle, title)
                .like(StringUtils.hasText(summary),Article::getSummary, summary);
        Page<Article> page = new Page<>(pageNum,pageSize);
        Page<Article> articlePage = page(page, wrapper);
        List<ArticleListVo> articleListVos = Convert.toList(ArticleListVo.class, articlePage.getRecords());
        PageVo pageVo = new PageVo(articleListVos, articlePage.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult removeArticleByIds(List<Long> ids) {
        for (Long id : ids) {
            Article article = getById(id);
            article.setDelFlag(1);
            updateById(article);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getInfoById(Long id) {
        Article vo = getById(id);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult updateArticleById(Article article) {
        updateById(article);
        return ResponseResult.okResult();
    }

}




