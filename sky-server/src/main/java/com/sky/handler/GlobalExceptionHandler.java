package com.sky.handler;

import com.sky.constant.MessageConstant;
import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.stream.Collectors;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice  // 声明一个控制器增强器
@Slf4j
public class GlobalExceptionHandler {

//    /*
//     * 捕获参数校验异常
//     */
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
//        log.error("参数校验异常：{}", ex.getMessage());
//        String message = ex.getBindingResult().getFieldErrors()
//                .stream()
//                .map(fieldError -> fieldError.getField() + "：" + fieldError.getDefaultMessage())
//                .collect(Collectors.joining(","));
//        return Result.error(message);
//    }

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    /**
     * 捕获SQL异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        log.error("数据库操作异常：{}", ex.getMessage());
        if (ex.getMessage().contains("Duplicate entry")) {
            String[] arr = ex.getMessage().split(" ");  // username
            String exMessage = arr[2] + MessageConstant.ALREADY_EXIST;
            return Result.error(exMessage);
        }
        return Result.error(MessageConstant.UNKNOWN_ERROR + ": " + ex.getMessage());
    }

}
