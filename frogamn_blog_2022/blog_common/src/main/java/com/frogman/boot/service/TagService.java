package com.frogman.boot.service;

import com.frogman.boot.domain.ResponseResult;
import com.frogman.boot.domain.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.frogman.boot.domain.vo.TagVo;

import java.util.List;

/**
* @author allenshen
* @description 针对表【fm_tag(标签)】的数据库操作Service
* @createDate 2022-10-27 21:28:30
*/
public interface TagService extends IService<Tag> {

    ResponseResult listTags(Integer pageNum, Integer pageSize, String name, String remark);

    ResponseResult addTag(Tag tag);

    ResponseResult removeTagById(List<Long> ids);

    ResponseResult getTagById(Long id);

    ResponseResult updateTageById(TagVo tagVo);

    ResponseResult listAllTag();
}
