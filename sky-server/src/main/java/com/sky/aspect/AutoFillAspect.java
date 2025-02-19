package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * @author uncle_yumo
 * @fileName AutoFillAspect
 * @createDate 2025/2/19 February
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

/*
 * 自动填充Aspect，实现公共字段自动填充功能
 *
*/
@Aspect  // 定义为Aspect切面
@Component  // 注册为Spring Bean
@Slf4j  // 日志打印
public class AutoFillAspect {

    // 定义切入点表达式，即哪些方法需要拦截
    /*
     * 所有com.sky.mapper包下的类中所有方法的切入点，execution表达式的语法
     * execution(* com.sky.mapper.*.*(..))：表示拦截所有com.sky.mapper包下的类中所有方法的调用
     * (..)：表示任意参数
     * @annotation(com.sky.annotation.AutoFill)：表示只有标注了@AutoFill注解的方法才会被拦截
     * 切入点表达式的写法：
     * execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)
     */
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointcut() {}

    // 定义通知，即拦截到切入点表达式的方法执行时要执行的操作
    /*
     * 前置通知，在目标方法执行前执行，可以对方法参数进行修改，对公共字段进行自动填充
     */
    @Before("autoFillPointcut()")  // 在autoFillPointcut()切入点表达式的方法执行前执行
    public void autoFill(JoinPoint joinPoint) {
        log.info("自动填充公共字段开始......");

        // 获取被拦截方法上的数据库操作类型
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();  // 获取被拦截方法的签名，即方法名
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);  // 获取被拦截方法上的@AutoFill注解
        OperationType operationType = autoFill.value();  // 获取@AutoFill注解的value属性值，即数据库操作类型

        // 获取到当前被拦截方法的参数列表  -- 实体对象
        Object[] args = joinPoint.getArgs();  // 获取被拦截方法的参数列表
        if (args == null || args.length == 0) {
            log.info("参数列表为空，不进行自动填充，方法名：{}", signature.getMethod().getName());
            return;  // 如果参数列表为空，则不进行自动填充
        }
        Object object = args[0];  // 获取第一个参数，即实体对象

        // 准备自动填充内容的数据  -- 自动填充值
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();

        // 根据操作类型对对应的属性进行赋值
        if (operationType == OperationType.INSERT) {
            // 对于新增操作，自动填充4个公共字段
            try {
                Method setCreateTime = object.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setCreateUser = object.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method setUpdateTime = object.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = object.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                // 通过反射设置自动填充值
                try {
                    setCreateUser.invoke(object, currentId);
                    setCreateTime.invoke(object, now);
                    setUpdateUser.invoke(object, currentId);
                    setUpdateTime.invoke(object, now);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    log.error("设置创建用户失败，方法名：{}", signature.getMethod().getName());
                    throw new RuntimeException(e);
                }

            } catch (NoSuchMethodException e) {
                log.error("实体类缺少必要的字段，方法名：{}", signature.getMethod().getName());
                throw new RuntimeException(e);
            }
        } else if (operationType == OperationType.UPDATE) {
            // 对于更新操作，自动填充2个公共字段
            try {
                Method setUpdateTime = object.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = object.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                // 通过反射设置自动填充值
                try {
                    setUpdateUser.invoke(object, currentId);
                    setUpdateTime.invoke(object, now);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    log.error("设置更新用户失败，方法名：{}", signature.getMethod().getName());
                    throw new RuntimeException(e);
                }

            } catch (NoSuchMethodException e) {
                log.error("实体类缺少必要的字段，方法名：{}", signature.getMethod().getName());
                throw new RuntimeException(e);
            }
        } else {
            log.info("不支持的数据库操作类型，方法名：{}", signature.getMethod().getName());
            return;  // 如果不支持的数据库操作类型，则不进行自动填充
        }
    }

}
