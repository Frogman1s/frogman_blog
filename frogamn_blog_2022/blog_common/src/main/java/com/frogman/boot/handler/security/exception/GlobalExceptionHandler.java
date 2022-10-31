package com.frogman.boot.handler.security.exception;

import com.frogman.boot.domain.ResponseResult;
import com.frogman.boot.domain.enums.AppHttpCodeEnum;
import com.frogman.boot.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException e){
        //打印异常信息
        log.error("出现异常,{$}",e);
        //从异常对象中获取提示信息
        //封装返回
        return ResponseResult.errorResult(e.getCode(),e.getMsg());
    }
    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e){
        //打印异常信息
        log.error("出现异常,{}",e);
        //从异常对象中获取提示信息
        //封装返回
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR,e.getMessage());
    }
}
