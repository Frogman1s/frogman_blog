package com.frogman.boot.controller;

import com.frogman.boot.domain.ResponseResult;
import com.frogman.boot.domain.dto.ArticleDto;
import com.frogman.boot.domain.enums.AppHttpCodeEnum;
import com.frogman.boot.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;
    @GetMapping("/system/role/list")
    public ResponseResult getRoleList(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize,
                                      @RequestParam(value = "roleName",required = false) String roleName,
                                      @RequestParam(value = "status",required = false) String status ){
        return roleService.getRoleList(pageNum,pageSize,roleName,status);

    }
    @PostMapping("/system/role")
    public ResponseResult noResponse01(){
        return ResponseResult.errorResult(AppHttpCodeEnum.TAG_ALREADY_EXIST,"该功能暂时关闭！");
    }

    @GetMapping("/system/menu/treeselect")
    public ResponseResult noResponse02(){
        return ResponseResult.errorResult(AppHttpCodeEnum.TAG_ALREADY_EXIST,"该功能暂时关闭！");
    }
    @GetMapping("/system/menu/treeselect/{id}")
    public ResponseResult noResponse03(){
        return ResponseResult.errorResult(AppHttpCodeEnum.TAG_ALREADY_EXIST,"该功能暂时关闭！");
    }
    @DeleteMapping("/system/role/{ids}")
    public ResponseResult noResponse04(){
        return ResponseResult.errorResult(AppHttpCodeEnum.TAG_ALREADY_EXIST,"该功能暂时关闭！");
    }
    @GetMapping("/system/menu/roleMenuTreeselect/{id}")
    public ResponseResult noResponse05(){
        return ResponseResult.errorResult(AppHttpCodeEnum.TAG_ALREADY_EXIST,"该功能暂时关闭！");
    }
}
