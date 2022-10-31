package com.frogman.boot.controller;

import com.frogman.boot.domain.ResponseResult;
import com.frogman.boot.domain.enums.AppHttpCodeEnum;
import com.frogman.boot.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MenuController {
    @Autowired
    private MenuService menuService;
    @GetMapping("/system/menu/list")
    public ResponseResult getRoleList(@RequestParam(value = "pageNum",required = false) Integer pageNum,
                                      @RequestParam(value = "pageSize",required = false) Integer pageSize,
                                      @RequestParam(value = "menuName",required = false) String menuName,
                                      @RequestParam(value = "status",required = false) String status ){
        return menuService.getMenuList(pageNum,pageSize,menuName,status);

    }
    @PostMapping("/system/menu")
    public ResponseResult noResponse01(){
        return ResponseResult.errorResult(AppHttpCodeEnum.TAG_ALREADY_EXIST,"该功能暂时关闭！");
    }
    @GetMapping("/system/menu/{id}")
    public ResponseResult noResponse02(){
        return ResponseResult.errorResult(AppHttpCodeEnum.TAG_ALREADY_EXIST,"该功能暂时关闭！");
    }
    @DeleteMapping("/system/menu/{id}")
    public ResponseResult noResponse03(){
        return ResponseResult.errorResult(AppHttpCodeEnum.TAG_ALREADY_EXIST,"该功能暂时关闭！");
    }

}
