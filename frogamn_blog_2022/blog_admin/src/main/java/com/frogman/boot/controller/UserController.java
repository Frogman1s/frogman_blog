package com.frogman.boot.controller;

import com.frogman.boot.domain.ResponseResult;
import com.frogman.boot.domain.dto.UserDto;
import com.frogman.boot.domain.vo.UserPutVo;
import com.frogman.boot.service.RoleService;
import com.frogman.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @GetMapping("/system/user/list")
    public ResponseResult userList(@RequestParam("pageNum") Integer pageNum,
                                       @RequestParam("pageSize") Integer pageSize,
                                       @RequestParam(value = "userName",required = false) String userName,
                                       @RequestParam(value = "phonenumber",required = false) String phonenumber,
                                       @RequestParam(value = "status",required = false) String status){
        return userService.userList(pageNum,pageSize,userName,phonenumber,status);

    }
    @PutMapping("/system/user/changeStatus")
    public ResponseResult changeLinkStatus(@RequestBody UserDto userDto){
        return userService.changeStatus(userDto.getUserId(),userDto.getStatus());
    }

    @DeleteMapping("/system/user/{ids}")
    public ResponseResult removeLinkByIds(@PathVariable List<Long> ids){
        return userService.removeUserByIds(ids);
    }
    @GetMapping("/system/role/listAllRole")
    public ResponseResult listAllRole(){
        return roleService.listAllRole();
    }
    @PostMapping("/system/user")
    public ResponseResult addUser(@RequestBody UserPutVo userPutVo) {
        return userService.addUser(userPutVo);
    }
}
