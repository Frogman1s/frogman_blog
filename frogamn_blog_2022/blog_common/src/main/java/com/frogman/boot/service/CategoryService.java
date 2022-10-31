package com.frogman.boot.service;

import com.frogman.boot.domain.ResponseResult;
import com.frogman.boot.domain.dto.CategoryDto;
import com.frogman.boot.domain.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author allenshen
* @description 针对表【fm_category(分类表)】的数据库操作Service
* @createDate 2022-10-21 23:04:58
*/
public interface CategoryService extends IService<Category> {
    ResponseResult<Category> getCategoryList();

    ResponseResult listAllCategory();

    ResponseResult categoryList(Integer pageNum, Integer pageSize, String name, String status);

    ResponseResult addCategory(CategoryDto categoryDto);

    ResponseResult removeCategoryByIds(List<Long> ids);

    ResponseResult getInfoById(Long id);

    ResponseResult updateCategoryById(CategoryDto categoryDto);
}
