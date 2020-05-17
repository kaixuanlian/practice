package com.liankaixuan;

import com.liankaixuan.annotation.CustomizeAutowired;
import com.liankaixuan.service.TransferMoneyService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther liankaixuan
 * @date 2020/5/16 10:25 下午
 */
@RestController
public class TestController {

    @CustomizeAutowired
    TransferMoneyService transferMoneyService;

    @RequestMapping("transfer")
    public void transfer(){
        transferMoneyService.transfer();
    }
}
