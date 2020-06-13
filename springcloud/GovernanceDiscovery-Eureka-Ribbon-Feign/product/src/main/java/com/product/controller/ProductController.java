package com.product.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.product.service.UserService;
import com.product.pojo.UserPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author mrliz
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired  // 注入RestTemplate
    private RestTemplate restTemplate = null;

    @Autowired
    private UserService userService;

    @GetMapping("/ribbon")
    public UserPo testRibbon(){

        UserPo userPo = null;

        // 循环10次，然后可以看到各个用户微服务后台的日志打印
        for (int i = 0; i < 10; i++) {
            //这里直接使用USER这个服务ID，代表用户微服务系统
            // 该ID通过属性spring.application.name来指定
            userPo = restTemplate.getForObject("http://USER/user/" + (i + 1), UserPo.class);
        }
        return userPo;
    }

    /**
     * 使用注入的UserService接口，调用声明的接口方法，完成对用户微服务的调用。Feign提供了负载均衡算法。
     * 与Ribbon相比，Feign屏蔽掉了RestTemplate的使用，提供了接口声明式的调用，代码可读性更高，多次调用更加方便。
     * @return
     */
    @GetMapping("/feign")
    public UserPo testFeign(){
        UserPo userPo = null;

        for (int i = 0; i < 10; i++) {
            Long id = (long)(i + 1);

            userService.getUser(id);
        }

        return userPo;
    }


    @GetMapping("/feign2")
    public Map<String,Object> testFeign2(){
        Map<String,Object> map = null;

        UserPo userPo = null;

        for (int i = 1; i <= 10; i++) {
            Long id = (long)(i + 1);

            userPo = new UserPo();

            userPo.setId(id);

            int level = i % 3 + 1;
            userPo.setLevel(level);

            userPo.setUserName("user_name_" + i);
            userPo.setNote("note_" + i);

            map = userService.addUser(userPo);
        }

        return map;
    }

    @GetMapping("/feign3")
    public Map<String,Object> testFeign3(){
        Map<String,Object> map = null;

        UserPo userPo = null;

        for (int i = 1; i <= 10; i++) {
            Long id = (long)(i + 1);

            String userName = "user_name_" + id;
            map = userService.updateUser(userName, id);
        }

        return map;
    }

    /**
     * Ribbon断路,默认超时时间为2000ms。
     * 只要请求该方法时，超时过了2000ms就会走error异常处理方法
     *
     * commandProperties = {@HystrixProperty(
     *             name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")}
     * 设置超时时间为3s
     * @return
     */
    @HystrixCommand(fallbackMethod = "error",commandProperties = {@HystrixProperty(
            name = "execution.isolation.thread.timeoutInMilliseconds",value = "2000")})
    @GetMapping("/circuitBreaker1")
    public String circuitBreaker1(){
        return restTemplate.getForObject("http://USER/timeOut",String.class);
    }

    /**
     * Feign断路
     * @return
     */
    @GetMapping("/circuitBreaker2")
    @HystrixCommand(fallbackMethod = "error")
    public String circuitBreaker2(){
        return userService.testTimeout();
    }

    private String error(){
        return "超时错误";
    }

}
