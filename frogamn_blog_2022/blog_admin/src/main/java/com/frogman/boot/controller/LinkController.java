package com.frogman.boot.controller;

import com.frogman.boot.domain.ResponseResult;
import com.frogman.boot.domain.entity.Link;
import com.frogman.boot.domain.vo.LinkVo;
import com.frogman.boot.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LinkController {
    @Autowired
    private LinkService linkService;
    @GetMapping("/content/link/list")
    public ResponseResult categoryList(@RequestParam("pageNum") Integer pageNum,
                                       @RequestParam("pageSize") Integer pageSize,
                                       @RequestParam(value = "name",required = false) String name,
                                       @RequestParam(value = "status",required = false) String status){
        return linkService.linkList(pageNum,pageSize,name,status);

    }
    @PostMapping("/content/link")
    public ResponseResult addCategory(@RequestBody Link link){
        return linkService.addLink(link);
    }

    @PutMapping("/content/link/changeLinkStatus")
    public ResponseResult changeLinkStatus(@RequestBody Link link){
        return linkService.changeLinkStatus(link.getId(),link.getStatus());
    }

    @DeleteMapping("/content/link/{ids}")
    public ResponseResult removeLinkByIds(@PathVariable List<Long> ids){
        return linkService.removeLinkByIds(ids);
    }

    @GetMapping("/content/link/{id}")
    public ResponseResult getInfoById(@PathVariable Long id){
        return linkService.getInfoById(id);
    }
    @PutMapping("/content/link")
    public ResponseResult updateCategoryById(@RequestBody LinkVo linkVo){
        return linkService.updateLinkById(linkVo);
    }
}
