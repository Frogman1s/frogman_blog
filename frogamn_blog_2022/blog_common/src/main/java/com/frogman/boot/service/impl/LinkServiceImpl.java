package com.frogman.boot.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frogman.boot.constants.SystemConstants;
import com.frogman.boot.domain.ResponseResult;
import com.frogman.boot.domain.entity.Link;
import com.frogman.boot.domain.vo.LinkVo;
import com.frogman.boot.domain.vo.PageVo;
import com.frogman.boot.service.LinkService;
import com.frogman.boot.mapper.LinkMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
* @author allenshen
* @description 针对表【fm_link(友链)】的数据库操作Service实现
* @createDate 2022-10-23 19:24:38
*/
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link>
    implements LinkService{

    @Override
    public ResponseResult getAllLink() {
        //查询所有审核通过的
        LambdaQueryWrapper<Link> wrapper = new LambdaQueryWrapper<Link>().eq(Link::getStatus, SystemConstants.LINK_STATUS_PASSED);
        List<Link> linkList = list(wrapper);
        List<LinkVo> linkVos = Convert.toList(LinkVo.class, linkList);
        return ResponseResult.okResult(linkVos);
    }

    @Override
    public ResponseResult linkList(Integer pageNum, Integer pageSize, String name, String status) {
        LambdaQueryWrapper<Link> wrapper = new LambdaQueryWrapper<Link>()
                .eq(Link::getDelFlag,0)
                .like(StringUtils.hasText(name), Link::getName, name)
                .like(StringUtils.hasText(status), Link::getStatus, status);
        Page<Link> page = new Page<>(pageNum, pageSize);
        Page<Link> linkPage = page(page, wrapper);
        List<LinkVo> linkVoList = Convert.toList(LinkVo.class, linkPage.getRecords());
        PageVo pageVo = new PageVo(linkVoList, linkPage.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addLink(Link link) {
        save(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult changeLinkStatus(Long id, String status) {
        Link link = getById(id);
        link.setStatus(status);
        updateById(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult removeLinkByIds(List<Long> ids) {
        for (Long id : ids) {
            LambdaUpdateWrapper<Link> wrapper = new LambdaUpdateWrapper<Link>()
                    .eq(Link::getId, id)
                    .set(Link::getDelFlag, 1);
            update(wrapper);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getInfoById(Long id) {
        LinkVo linkVo = Convert.convert(LinkVo.class, getById(id));
        return ResponseResult.okResult(linkVo);
    }

    @Override
    public ResponseResult updateLinkById(LinkVo linkVo) {
        updateById(Convert.convert(Link.class,linkVo));
        return ResponseResult.okResult();
    }
}




