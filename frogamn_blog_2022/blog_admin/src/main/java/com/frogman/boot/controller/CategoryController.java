package com.frogman.boot.controller;

import com.frogman.boot.domain.ResponseResult;
import com.frogman.boot.domain.dto.CategoryDto;
import com.frogman.boot.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/content/category/list")
    public ResponseResult categoryList(@RequestParam("pageNum") Integer pageNum,
                                       @RequestParam("pageSize") Integer pageSize,
                                       @RequestParam(value = "name",required = false) String name,
                                       @RequestParam(value = "status",required = false) String status){
        return categoryService.categoryList(pageNum,pageSize,name,status);

    }
    @PostMapping("/content/category")
    public ResponseResult addCategory(@RequestBody CategoryDto categoryDto){
        return categoryService.addCategory(categoryDto);
    }
    @DeleteMapping("/content/category/{ids}")
    public ResponseResult removeCategoryByIds(@PathVariable List<Long> ids){
        return categoryService.removeCategoryByIds(ids);
    }

    @GetMapping("/content/category/{id}")
    public ResponseResult getInfoById(@PathVariable Long id){
        return categoryService.getInfoById(id);
    }

    @PutMapping("/content/category")
    public ResponseResult updateCategoryById(@RequestBody CategoryDto categoryDto){
        return categoryService.updateCategoryById(categoryDto);
    }




}
