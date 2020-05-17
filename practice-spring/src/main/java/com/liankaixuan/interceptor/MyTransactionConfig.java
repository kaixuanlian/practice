package com.liankaixuan.interceptor;

import com.liankaixuan.annotation.CustomizeTransactional;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @auther liankaixuan
 * @date 2020/5/17 4:57 下午
 */
//@Configuration
//public class MyTransactionConfig {
//
//    @Bean
//    public DefaultPointcutAdvisor defaultPointcutAdvisor(){
//        AnnotationMatchingPointcut pointcut = new AnnotationMatchingPointcut(null, CustomizeTransactional.class);
//        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
//        advisor.setPointcut(pointcut);
//        advisor.setAdvice(new MyTransactionInterceptor());
//        return advisor;
//    }
//
//}
