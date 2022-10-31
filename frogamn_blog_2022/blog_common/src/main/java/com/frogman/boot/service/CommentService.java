package com.frogman.boot.service;

import com.frogman.boot.domain.ResponseResult;
import com.frogman.boot.domain.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author allenshen
* @description 针对表【fm_comment(评论表)】的数据库操作Service
* @createDate 2022-10-24 22:04:47
*/
public interface CommentService extends IService<Comment> {

    ResponseResult getCommentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);

}
