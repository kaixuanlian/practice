package com.liankaixuan.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @auther liankaixuan
 * @date 2020/5/17 5:35 下午
 */
@Aspect
@Component
public class TransactionAOP {

    @Pointcut("@annotation(com.liankaixuan.annotation.CustomizeTransactional)")
    public void monitor() {
    }

    @Before(value = "monitor()")
    public void before(ProceedingJoinPoint joinPoint){
        System.out.println("==========");
    }
}
