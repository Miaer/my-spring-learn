package springboot.learning.aop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import springboot.learning.aop.promise.interceptor.impl.MyInterceptor;
import springboot.learning.aop.promise.proxy.ProxyBean;
import springboot.learning.aop.promise.service.HelloService;
import springboot.learning.aop.promise.service.impl.HelloServiceImpl;

@SpringBootTest
class AopApplicationTests {

    @Test
    void contextLoads() {
        HelloServiceImpl helloService = new HelloServiceImpl();

        // 按照约定获取proxy
        HelloService proxy = (HelloService) ProxyBean.getProxyBean(helloService, new MyInterceptor());

        proxy.sayHello("zhangsan");

        System.out.println("n##########  name is null");

        proxy.sayHello("Lisi");

        proxy.sayHello(null);
    }

}
