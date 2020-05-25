package springboot.learning.aop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import springboot.learning.aop.interceptor.impl.MyInterceptor;
import springboot.learning.aop.proxy.ProxyBean;
import springboot.learning.aop.service.HelloService;
import springboot.learning.aop.service.impl.HelloServiceImpl;

@SpringBootTest
class AopApplicationTests {

    @Test
    void contextLoads() {
        HelloServiceImpl helloService = new HelloServiceImpl();

        // 按照约定获取proxy
        HelloService proxy = (HelloService) ProxyBean.getProxyBean(helloService, new MyInterceptor());

        proxy.sayHello("zhangsan");

        System.out.println("\n##########  name is null");

        proxy.sayHello("Lisi");

        proxy.sayHello(null);
    }

}
