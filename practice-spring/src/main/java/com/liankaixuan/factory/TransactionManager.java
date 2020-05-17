package com.liankaixuan.factory;

import com.liankaixuan.annotation.CustomizeService;

/**
 * @auther liankaixuan
 * @date 2020/5/16 11:45 下午
 */
//@CustomizeService
public class TransactionManager {

    public void before(){
        System.out.println("== 开启事务 ==");
    }

    public void after(){
        System.out.println("== 执行正常，提交事务 ==");
    }

    public void afterError(){
        System.out.println("== 出现错误，回滚事务 ==");
    }
}
