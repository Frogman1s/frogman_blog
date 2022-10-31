package com.frogman.boot.service;

import com.frogman.boot.domain.ResponseResult;
import com.frogman.boot.domain.entity.Link;
import com.baomidou.mybatisplus.extension.service.IService;
import com.frogman.boot.domain.vo.LinkVo;

import java.util.List;

/**
* @author allenshen
* @description 针对表【fm_link(友链)】的数据库操作Service
* @createDate 2022-10-23 19:24:38
*/
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

    ResponseResult linkList(Integer pageNum, Integer pageSize, String name, String status);

    ResponseResult addLink(Link link);

    ResponseResult changeLinkStatus(Long id, String status);

    ResponseResult removeLinkByIds(List<Long> ids);

    ResponseResult getInfoById(Long id);

    ResponseResult updateLinkById(LinkVo linkVo);
}
