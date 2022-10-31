package com.frogman.boot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frogman.boot.domain.entity.ArticleTag;
import com.frogman.boot.service.ArticleTagService;
import com.frogman.boot.mapper.ArticleTagMapper;
import org.springframework.stereotype.Service;

/**
* @author allenshen
* @description 针对表【fm_article_tag(文章标签关联表)】的数据库操作Service实现
* @createDate 2022-10-29 12:16:18
*/
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag>
    implements ArticleTagService{

}




