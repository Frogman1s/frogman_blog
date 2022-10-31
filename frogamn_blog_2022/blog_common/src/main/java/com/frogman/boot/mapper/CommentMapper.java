package com.frogman.boot.mapper;

import com.frogman.boot.domain.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author allenshen
* @description 针对表【fm_comment(评论表)】的数据库操作Mapper
* @createDate 2022-10-24 22:04:47
* @Entity com.frogman.boot.domain.entity.Comment
*/
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}




