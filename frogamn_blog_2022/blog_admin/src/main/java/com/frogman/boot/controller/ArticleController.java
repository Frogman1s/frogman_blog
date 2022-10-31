package com.frogman.boot.controller;

import com.frogman.boot.domain.ResponseResult;
import com.frogman.boot.domain.entity.Article;
import com.frogman.boot.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/content/article/list")
    public ResponseResult getArticleList(@RequestParam("pageNum") Integer pageNum,
                                         @RequestParam("pageSize") Integer pageSize,
                                         @RequestParam(value = "title",required = false) String title,
                                         @RequestParam(value = "summary",required = false) String summary){
        return articleService.getArticleList(pageNum,pageSize,title,summary);

    }

//    @PostMapping("/content/article")
//    public ResponseResult addCategory(@RequestBody ArticleListVo articleListVo){
//        return ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH,"此处不行允许新增文章，请去'写博客'");
//
//    }

    @DeleteMapping("/content/article/{ids}")
    public ResponseResult removeArticleByIds(@PathVariable List<Long> ids){
        return articleService.removeArticleByIds(ids);
    }

    @GetMapping("/content/article/{id}")
    public ResponseResult getInfoById(@PathVariable Long id){
        return articleService.getInfoById(id);
    }

    @PutMapping("/content/article")
    public ResponseResult updateArticleById(@RequestBody Article article){
        return articleService.updateArticleById(article);
    }



}
