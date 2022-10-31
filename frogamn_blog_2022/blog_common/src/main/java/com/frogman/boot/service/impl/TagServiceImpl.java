package com.frogman.boot.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frogman.boot.constants.SystemConstants;
import com.frogman.boot.domain.ResponseResult;
import com.frogman.boot.domain.entity.Tag;
import com.frogman.boot.domain.enums.AppHttpCodeEnum;
import com.frogman.boot.domain.vo.PageVo;
import com.frogman.boot.domain.vo.TagVo;
import com.frogman.boot.exception.SystemException;
import com.frogman.boot.service.TagService;
import com.frogman.boot.mapper.TagMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
* @author allenshen
* @description 针对表【fm_tag(标签)】的数据库操作Service实现
* @createDate 2022-10-27 21:28:30
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService{

    @Override
    public ResponseResult listTags(Integer pageNum, Integer pageSize, String name, String remark) {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<Tag>()
                .like(StringUtils.hasText(name),Tag::getName,name)
                .like(StringUtils.hasText(remark),Tag::getRemark,remark)
                .select(Tag::getId,Tag::getName,Tag::getRemark);
        Page<Tag> page = new Page<>(pageNum,pageSize);
        Page<Tag> tagPage = page(page, wrapper);
        long total = tagPage.getTotal();
        List<TagVo> tagVoList = Convert.toList(TagVo.class, tagPage.getRecords());
        PageVo pageVo = new PageVo(tagVoList, total);
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addTag(Tag tag)throws SystemException {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<Tag>()
                .eq(Tag::getName, tag.getName())
                .eq(Tag::getDelFlag, 0);
        if(Objects.isNull(getOne(wrapper))){
            save(tag);
            return ResponseResult.okResult();
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.TAG_ALREADY_EXIST);

    }

    @Override
    public ResponseResult removeTagById(List<Long> ids) {
        for (Long id : ids) {
            LambdaUpdateWrapper<Tag> wrapper = new LambdaUpdateWrapper<Tag>()
                    .eq(Tag::getId, id)
                    .set(Tag::getDelFlag, 1);
            update(wrapper);

        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getTagById(Long id) {
        TagVo tagVo = Convert.convert(TagVo.class, getById(id));
        return ResponseResult.okResult(tagVo);
    }

    @Override
    public ResponseResult updateTageById(TagVo tagVo) {
        Long id = tagVo.getId();
        String name = tagVo.getName();
        String remark = tagVo.getRemark();
        LambdaUpdateWrapper<Tag> wrapper = new LambdaUpdateWrapper<Tag>()
                .eq(Tag::getId, id)
                .set(Tag::getName, name)
                .set(Tag::getRemark, remark);
        update(wrapper);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllTag() {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<Tag>()
                .eq(Tag::getDelFlag, 0);
        List<TagVo> tagVoList = Convert.toList(TagVo.class, list(wrapper));
        return ResponseResult.okResult(tagVoList);
    }
}




