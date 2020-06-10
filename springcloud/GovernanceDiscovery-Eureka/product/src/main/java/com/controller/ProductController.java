package com.controller;


import com.user.pojo.UserPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author mrliz
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired  // 注入RestTemplate
    private RestTemplate restTemplate = null;

    public UserPo testRibbon(){
        UserPo userPo = null;

        // 循环10次，然后可以看到各个用户微服务后台的日志打印
        for (int i = 0; i < 10; i++) {
            //这里直接使用USER这个服务ID，代表用户微服务系统
            // 该ID通过属性spring.application.name来指定
            restTemplate.getForObject("http://USER/user/" + (i + 1),UserPo.class);
        }
        return userPo;
    }
}
