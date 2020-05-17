package com.liankaixuan.domain;

import com.liankaixuan.annotation.CustomizeAutowired;

/**
 * @auther liankaixuan
 * @date 2020/5/9 8:50 下午
 */
public class MyBean {

	@CustomizeAutowired(value = "myBeanTest")
	MyBeanTest myBeanTest;

	String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public MyBean(){
//		System.out.println(" myBean 构造方法执行了");
	}

	public MyBeanTest getMyBeanTest() {
		return myBeanTest;
	}

	public void setMyBeanTest(MyBeanTest myBeanTest) {
		this.myBeanTest = myBeanTest;
	}

	@Override
	public String toString() {
		return "MyBean{" +
				"name='" + name + '\'' +
				'}';
	}
}
