package springboot.learning.aop.service.impl;

import springboot.learning.aop.service.HelloService;

/**
 * @author mrliz
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public void sayHello(String name) {

        if (name == null || name == "")
            throw new RuntimeException("parameter is null !!");

        System.out.println("hello " + name);
    }
}
