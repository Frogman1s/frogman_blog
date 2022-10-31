package com.frogman.boot.controller;

import com.frogman.boot.domain.ResponseResult;
import com.frogman.boot.domain.entity.Category;
import com.frogman.boot.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;


    @GetMapping("/getCategoryList")
    public ResponseResult<Category> getCategoryList(){
        return categoryService.getCategoryList();
    }

}
