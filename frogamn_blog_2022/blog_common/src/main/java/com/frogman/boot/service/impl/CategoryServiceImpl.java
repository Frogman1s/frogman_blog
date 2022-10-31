package com.frogman.boot.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frogman.boot.constants.SystemConstants;
import com.frogman.boot.domain.ResponseResult;
import com.frogman.boot.domain.dto.CategoryDto;
import com.frogman.boot.domain.entity.Article;
import com.frogman.boot.domain.entity.Category;
import com.frogman.boot.domain.entity.Tag;
import com.frogman.boot.domain.enums.AppHttpCodeEnum;
import com.frogman.boot.domain.vo.CategoryVo;
import com.frogman.boot.domain.vo.PageVo;
import com.frogman.boot.service.ArticleService;
import com.frogman.boot.service.CategoryService;
import com.frogman.boot.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author allenshen
* @description 针对表【fm_category(分类表)】的数据库操作Service实现
* @createDate 2022-10-21 23:04:58
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

    @Autowired
    ArticleServiceImpl articleService;

    @Override
    public ResponseResult<Category> getCategoryList() {
        //查询已发布的文章表
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(wrapper);
        //获取文章的分类id，去重
        Set<Long> set = articleList.stream()
                .map(Article::getCategoryId)
                .collect(Collectors.toSet());
        //查询分类表
        List<Category> categoryList = listByIds(set);
        List<Category> categories = categoryList.stream()
                .filter(category -> SystemConstants.CATEGORY_STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        //封装数据
        List<CategoryVo> list = Convert.toList(CategoryVo.class, categories);
        return ResponseResult.okResult(list);
    }

    @Override
    public ResponseResult listAllCategory() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<Category>().eq(Category::getDelFlag, 0)
                .eq(Category::getStatus, 0);
        List<CategoryVo> categoryVoList = Convert.toList(CategoryVo.class, list(wrapper));

        return ResponseResult.okResult(categoryVoList);
    }

    @Override
    public ResponseResult categoryList(Integer pageNum, Integer pageSize, String name, String status) {
        Page<Category> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<Category>()
                .eq(Category::getDelFlag,0)
                .like(StringUtils.hasText(name), Category::getName, name)
                .like(StringUtils.hasText(status), Category::getStatus, status);
        Page<Category> categoryPage = page(page, wrapper);
        PageVo pageVo = new PageVo(categoryPage.getRecords(), categoryPage.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addCategory(CategoryDto categoryDto) {
        save(Convert.convert(Category.class,categoryDto));
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult removeCategoryByIds(List<Long> ids) {
        for (Long id : ids) {
            LambdaUpdateWrapper<Category>wrapper = new LambdaUpdateWrapper<Category>()
                    .eq(Category::getId, id)
                    .set(Category::getDelFlag, 1);
            update(wrapper);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getInfoById(Long id) {
        CategoryDto categoryDto = Convert.convert(CategoryDto.class, getById(id));
        return ResponseResult.okResult(categoryDto);
    }

    @Override
    public ResponseResult updateCategoryById(CategoryDto categoryDto) {
        LambdaUpdateWrapper<Category> wrapper = new LambdaUpdateWrapper<Category>()
                .eq(Category::getId, categoryDto.getId())
                .set(Category::getName, categoryDto.getName())
                .set(Category::getStatus, categoryDto.getStatus())
                .set(Category::getDescription, categoryDto.getDescription());
        boolean b = update(wrapper);
        return ResponseResult.okResult();
    }

}




