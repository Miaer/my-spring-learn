package com.example.treadpool;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncServiceImpl implements AsyncService{
    @Override
    @Async  // 异步调用
    public void generateReport() {
        System.out.println("报表线程名称：" + Thread.currentThread().getName());
    }
}
