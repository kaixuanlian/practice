package com.liankaixuan.interceptor;

import com.liankaixuan.annotation.CustomizeTransactional;
import com.liankaixuan.factory.TransactionManager;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @auther liankaixuan
 * @date 2020/5/16 10:58 下午
 */
public class MyTransactionInterceptor implements InvocationHandler, MethodInterceptor {

    public Object object;

    TransactionManager transactionManager = new TransactionManager();

    public MyTransactionInterceptor(Object o){
        this.object = o;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        CustomizeTransactional annotation = method.getAnnotation(CustomizeTransactional.class);
        if (annotation == null){
            return method.invoke(object, args);
        }
        Object result = null;
        transactionManager.before();
        try {
            result = method.invoke(object, args);
        }catch (Exception e){
            transactionManager.afterError();
            throw e;
        }
        transactionManager.after();
        return result;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        CustomizeTransactional annotation = method.getAnnotation(CustomizeTransactional.class);
        if (annotation == null){
            return method.invoke(object, objects);
        }
        Object result = null;
        transactionManager.before();
        try {
            result = method.invoke(object, objects);
        }catch (Exception e){
            transactionManager.afterError();
            throw e;
        }
        transactionManager.after();
        return result;
    }

}
