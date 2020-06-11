package com.controller;


import com.service.UserService;
import com.user.pojo.UserPo;
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

            map = userService.updateUser("user_name_" + id, id);
        }

        return map;
    }

}
