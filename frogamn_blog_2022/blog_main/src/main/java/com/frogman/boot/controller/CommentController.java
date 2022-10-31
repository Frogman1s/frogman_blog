package com.frogman.boot.controller;

import com.frogman.boot.constants.SystemConstants;
import com.frogman.boot.domain.ResponseResult;
import com.frogman.boot.domain.entity.Comment;
import com.frogman.boot.service.CommentService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@Api(tags = "评论",description = "评论相关接口")
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/commentList")
    public ResponseResult getCommentList(@RequestParam("articleId") Long articleId,
                                         @RequestParam("pageNum") Integer pageNum,
                                         @RequestParam("pageSize") Integer pageSize){
        return commentService.getCommentList(SystemConstants.ARTICLE_COMMENT,articleId,pageNum,pageSize);
    }

    @PostMapping
    public ResponseResult addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);

    }

    @GetMapping("/linkCommentList")
    public ResponseResult getLinkCommentList(
                                         @RequestParam("pageNum") Integer pageNum,
                                         @RequestParam("pageSize") Integer pageSize){
        return commentService.getCommentList(SystemConstants.LINK_COMMENT,null,pageNum,pageSize);
    }

}
