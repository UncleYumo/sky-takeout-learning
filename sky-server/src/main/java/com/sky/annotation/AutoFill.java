package com.sky.annotation;

import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author uncle_yumo
 * @fileName AutoFill
 * @createDate 2025/2/19 February
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

/*
 * 自定义注解，该注解用于自动填充字段
 */
@Target(ElementType.METHOD)  // 注解作用于方法上
@Retention(RetentionPolicy.RUNTIME)  // 注解在运行时有效
public @interface AutoFill {
    // 指定需要自动填充的数据库操作类型
    OperationType value();  // OperationType value()方法返回值类型为OperationType，value()方法为注解属性
}
