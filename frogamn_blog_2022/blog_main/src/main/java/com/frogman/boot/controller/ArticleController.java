package com.frogman.boot.controller;

import com.frogman.boot.domain.ResponseResult;
import com.frogman.boot.domain.entity.Article;
import com.frogman.boot.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/article")
@RestController
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){
        return articleService.hotArticleList();
    }

    @GetMapping("/articleList")
    public ResponseResult<Article> articleList(@RequestParam(value = "categoryId",required = false) Long categoryId,
                                               @RequestParam("pageNum")Integer pageNum,
                                               @RequestParam("pageSize") Integer pageSize){
        return articleService.articleList(categoryId,pageNum,pageSize);
    }

    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id){
        return articleService.getArticleDetail(id);
    }

    @PutMapping("/updateViewCount/{id}")
    public ResponseResult updateViewCount(@PathVariable Long id){
        return articleService.updateViewCount(id);
    }
}
