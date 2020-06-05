package com.example.treadpool.poolconfig;

import com.example.treadpool.TreadPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import java.util.ArrayList;
import java.util.Iterator;

@Slf4j
public class TestShreadPool {
    @Autowired
    TreadPoolConfig treadPoolConfig;

    @Test
    @Async("executor1")
    public void test() throws InterruptedException {
        treadPoolConfig.executor1();
        Thread.sleep(2000);
        temp();
        test1();
    }

    @Test
    @Async("executor1")
    public void test1(){
        temp();
    }
    protected void temp(){
        log.info("开始.。。。。。。。。。。。。。");
        log.info("结束.。。。。。。。。。。。。。");
        System.out.println(Thread.currentThread().getName() + "开始.。。。。。。。。。。。。。");
        System.out.println(Thread.currentThread().getName() + "结束.。。。。。。。。。。。。。");
    }

    @Test
    public void testListIeta(){
        ArrayList<Object> arrayList = new ArrayList<>();
        Iterator<Object> iterator = arrayList.iterator();
        iterator.remove();
    }
}
