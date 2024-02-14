package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;


@Slf4j
@Component
@Aspect
public class AutoFillAspect {

    @Pointcut("execution(* com.sky.mapper.*.* (..))&& @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut(){};

    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        log.info("公共字段自动填充！！！开始！！");
        //获取方法的签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AutoFill annotation =signature.getMethod().getAnnotation(AutoFill.class); //获取方法上的注解对对象
        OperationType value = annotation.value();

        //获取方法的参数
        Object[] args = joinPoint.getArgs();
        Object arg = args[0];
        //获取参数的实体对象的方法

        Method setUpdateTime = arg.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
        Method setUpdateUser = arg.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
        //准备参数
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();
        //判断类别 并调用方法
        if (value==OperationType.INSERT){
            Method setCreateTime = arg.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
            Method setCreateUser = arg.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
            setCreateUser.invoke(arg,currentId);
            setCreateTime.invoke(arg,now);
            setUpdateUser.invoke(arg,currentId);
            setUpdateTime.invoke(arg,now);
        }else if(value==OperationType.UPDATE){
            setUpdateUser.invoke(arg,currentId);
            setUpdateTime.invoke(arg,now);
        }


    }
}
