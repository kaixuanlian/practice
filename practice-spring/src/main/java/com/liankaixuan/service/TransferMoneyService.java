package com.liankaixuan.service;

import com.liankaixuan.annotation.CustomizeService;
import com.liankaixuan.annotation.CustomizeTransactional;

/**
 * @auther liankaixuan
 * @date 2020/5/16 5:27 下午
 */
@CustomizeService(value = "123")
//@Component
public class TransferMoneyService {

	@CustomizeTransactional
	public void transfer(){
		int num = 1/0;
	}
}
