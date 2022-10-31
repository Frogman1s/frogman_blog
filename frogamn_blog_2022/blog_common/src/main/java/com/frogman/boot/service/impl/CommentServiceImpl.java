package com.frogman.boot.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frogman.boot.constants.SystemConstants;
import com.frogman.boot.domain.ResponseResult;
import com.frogman.boot.domain.entity.Comment;
import com.frogman.boot.domain.enums.AppHttpCodeEnum;
import com.frogman.boot.domain.vo.CommentVo;
import com.frogman.boot.domain.vo.PageVo;
import com.frogman.boot.exception.SystemException;
import com.frogman.boot.service.CommentService;
import com.frogman.boot.mapper.CommentMapper;
import com.frogman.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
* @author allenshen
* @description 针对表【fm_comment(评论表)】的数据库操作Service实现
* @createDate 2022-10-24 22:04:47
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{
    @Autowired
    UserService userService;

    @Override
    public ResponseResult getCommentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        //查询当前文章的根评论
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<Comment>()
                .eq(SystemConstants.ARTICLE_COMMENT.equals(commentType),Comment::getArticleId, articleId)//对articleId进行判断
                .eq(Comment::getRootId, -1)//根评论 rootId=-1
                .eq(Comment::getType, commentType)//评论类型
                .orderByDesc(Comment::getCreateTime);
        //分页查询
        Page<Comment> page = new Page<>(pageNum,pageSize);
        Page<Comment> commentPage = page(page, wrapper);
        List<CommentVo> commentVos = toCommentVoList(commentPage.getRecords());

        //查询所有根评论的子评论
        for (CommentVo vo : commentVos) {
            //查询子评论
            List<CommentVo> children=getChildren(vo.getId());
            //赋值
            vo.setChildren(children);
        }
        return ResponseResult.okResult(new PageVo(commentVos,commentPage.getTotal()));
    }

    @Override
    public ResponseResult addComment(Comment comment) {
        if(!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_IS_EMPTY);
        }
        save(comment);
        return ResponseResult.okResult();
    }


    /***
     * 根据根评论id查询其所有子评论
     * @param id
     * @return
     */
    private List<CommentVo> getChildren(Long id) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<Comment>()
                .eq(Comment::getRootId, id)
                .orderByDesc(Comment::getCreateTime);
        List<Comment> commentList = list(wrapper);
        return toCommentVoList(commentList);
    }

    /***
     * List<Comment>转成List<CommentVo>
     * @param list
     * @return
     */
    private List<CommentVo> toCommentVoList(List<Comment> list){
        List<CommentVo> commentVos = Convert.toList(CommentVo.class, list);
        for (CommentVo item:commentVos) {
            //通过createBy 查username
            String username = userService.getById(item.getCreateBy()).getNickName();
//            String avatar = userService.getById(item.getCreateBy()).getAvatar();
            item.setUsername(username);
//            item.setAvatar(avatar);
            //通过toCommentId查toCommentUserName
            if(item.getToCommentUserId()!=-1){
                String name = userService.getById(item.getToCommentUserId()).getNickName();
                item.setToCommentUserName(name);
            }
        }
        return commentVos;
    }
}




