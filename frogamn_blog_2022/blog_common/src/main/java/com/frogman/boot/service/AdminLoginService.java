package com.frogman.boot.service;

import com.frogman.boot.domain.ResponseResult;
import com.frogman.boot.domain.entity.User;

public interface AdminLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
