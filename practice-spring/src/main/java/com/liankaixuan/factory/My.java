//package com.liankaixuan.factory;
//
//import org.springframework.aop.TargetSource;
//import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.PropertyValues;
//import org.springframework.stereotype.Component;
//
//import java.beans.PropertyDescriptor;
//
///**
// * @auther liankaixuan
// * @date 2020/5/17 4:16 下午
// */
//@Component
//public class My extends AbstractAutoProxyCreator {
//
////    ApplicationContext applicationContext;
//
//    @Override
//    protected Object[] getAdvicesAndAdvisorsForBean(Class<?> aClass, String s, TargetSource targetSource) throws BeansException {
//        System.out.println("代理对象===" + aClass);
//        return new Object[0];
//    }
//
//    @Override
//    public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
//        return null;
//    }
//}
