package com.frogman.boot.controller;

import com.frogman.boot.domain.ResponseResult;
import com.frogman.boot.domain.dto.ArticleDto;
import com.frogman.boot.domain.entity.Article;
import com.frogman.boot.service.ArticleService;
import com.frogman.boot.service.CategoryService;
import com.frogman.boot.service.TagService;
import com.frogman.boot.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class BlogContentController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TagService tagService;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private ArticleService articleService;

    @GetMapping("/content/category/listAllCategory")
    public ResponseResult listAllCategory(){
        return categoryService.listAllCategory();
    }

    @GetMapping("/content/tag/listAllTag")
    public  ResponseResult listAllTag(){
        return tagService.listAllTag();
    }

    @PostMapping("/upload")
    public  ResponseResult uploadImg(@RequestParam("img") MultipartFile file){
        return uploadService.uploadImg(file);
    }

    @PostMapping("/content/article")
    public ResponseResult addArticle(@RequestBody ArticleDto articleDto){
            return articleService.addArticle(articleDto);
    }

}
