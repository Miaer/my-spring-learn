package com.example.treadpool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author mrliz
 */
@Controller
@RequestMapping("/async")
public class AsyncController {

    @Autowired
    private AsyncService service;

    @GetMapping("/asyncPage")
    public String asyncPage(){
        System.out.println("请求线程名称为:" + Thread.currentThread().getName());
        service.generateReport();
        return "async";
    }
}
