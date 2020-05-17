package com.liankaixuan.domain;

/**
 * @auther liankaixuan
 * @date 2020/5/16 5:10 下午
 */
public class MyBeanTest {

	String name = "lalala";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "MyBeanTest{" +
				"name='" + name + '\'' +
				'}';
	}
}
