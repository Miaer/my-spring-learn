package com.service;

import com.user.pojo.UserPo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Feign接口
 * @author mrliz
 */
// 指定服务ID
@FeignClient("USER")
public interface UserService {

    // 指定通过HTTP的GET方法请求路径
    @GetMapping("/user/{id}")
    // 这里会采用spring MVC的注解配置
    UserPo getUser(@PathVariable("id")Long id);


    /**
     * post方法请求用户微服务
     * 请求路径和用户微服务的定义保持一致
     * @param user
     * @return
     */
    @PostMapping("/insert")
    Map<String,Object> addUser(@RequestBody UserPo user);

    /**
     * post方法请求用户微服务,和用户微服务定义保持一致
     * @param userName
     * @param id
     * @return
     */
    @PostMapping("/update/{userName}")
    Map<String,Object> updateUser(@PathVariable("userName") String userName, @RequestHeader("id")Long id);


    @GetMapping("/timeOut")
    public String testTimeout();
}
