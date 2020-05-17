package com.liankaixuan.factory;

import com.liankaixuan.annotation.CustomizeAutowired;
import com.liankaixuan.interceptor.MyTransactionInterceptor;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @auther liankaixuan
 * @date 2020/5/16 11:13 下午
 */
//@Component
public class ProxyFactory {

//    @CustomizeAutowired
    TransactionManager transactionManager = new TransactionManager();

    public static ProxyFactory instance = new ProxyFactory();

    public Object getProxy(Object object){
        MyTransactionInterceptor interceptor = new MyTransactionInterceptor(object);
        if (object.getClass().getInterfaces() == null || object.getClass().getInterfaces().length == 0){
            return cglibProxy(object, (MethodInterceptor) interceptor);
        }
        return jdkProxy(object, interceptor);
    }


    public Object jdkProxy(Object object, MyTransactionInterceptor interceptor){
        return Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(), interceptor);
    }

    public Object cglibProxy(Object obj, MethodInterceptor interceptor){
        Object proxy = Enhancer.create(obj.getClass(), interceptor);
        return proxy;
    }
}
