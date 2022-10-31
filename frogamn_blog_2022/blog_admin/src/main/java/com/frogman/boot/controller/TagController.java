package com.frogman.boot.controller;

import com.frogman.boot.domain.ResponseResult;
import com.frogman.boot.domain.entity.Tag;
import com.frogman.boot.domain.vo.TagVo;
import com.frogman.boot.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public ResponseResult list(@RequestParam("pageNum")Integer pageNum,
                               @RequestParam("pageSize")Integer pageSize,
                               @RequestParam(value = "name",required = false)String name,
                               @RequestParam(value = "remark",required = false)String remark
    ){
        return tagService.listTags(pageNum,pageSize,name,remark);
    }
    @PostMapping
    public ResponseResult addTag(@RequestBody Tag tag){
        return tagService.addTag(tag);
    }
    @DeleteMapping("/{ids}")
    public ResponseResult removeTagById(@PathVariable List<Long> ids){
        return tagService.removeTagById(ids);
    }
    @GetMapping("/{id}")
    public ResponseResult getTagById(@PathVariable Long id){
        return tagService.getTagById(id);
    }
    @PutMapping
    public ResponseResult updateTageById(@RequestBody TagVo tagVo){
        return tagService.updateTageById(tagVo);
    }

}
