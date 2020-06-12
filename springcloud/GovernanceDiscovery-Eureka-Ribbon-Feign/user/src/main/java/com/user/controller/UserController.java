package com.user.controller;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import com.user.pojo.UserPo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mrliz
 */
@RestController
@Slf4j
public class UserController {

    // 服务发现客户端
    @Autowired
    private DiscoveryClient discoveryClient = null;

    /**
     * DiscoveryClient 是spring boot自动创建的。
     * 在方法中打印第一个用户微服务ID、主机和端口
     * @param id
     * @return
     */
    @GetMapping("/user/{id}")
    public UserPo getUserPo(@PathVariable("id") Long id){
        ServiceInstance service = discoveryClient.getInstances("USER").get(0);

        log.info("【" + service.getServiceId() + "】:" + service.getHost() + ":"+service.getHost());

        UserPo userPo = new UserPo();
        userPo.setId(id);
        userPo.setLevel((int)(id%3+1));
        userPo.setUserName("user_name_" + id);
        userPo.setNote("note_" + id);

        return userPo;
    }

    /**
     * 新增用户，POST请求，以body形式传递
     * @param userPo
     * @return
     */
    @PostMapping("/insert")
    public Map<String,Object> insertUser(@RequestBody UserPo userPo){
        HashMap<String, Object> map = new HashMap<>();

        map.put("success",true);
        map.put("message","插入用户信息【" + userPo.getUserName() + "】成功");
        return map;
    }

    /**
     * 修改用户名，POST请求，其中用户编号使用请求的形式传递
     * @param userName
     * @param id
     * @return
     */
    @PostMapping("/update/{userName}")
    public Map<String,Object> updateUsername(@PathVariable("userName")String userName,@RequestHeader("id")Long id){
        HashMap<String, Object> map = new HashMap<>();

        map.put("success",true);
        map.put("message","更新用户【"+id+"】名称【" + userName + "】成功");
        return map;
    }

    /**
     * hystrix降级服务超时方法
     * @return
     */
    @GetMapping("/timeOut")
    public String timeOut(){

        long ms = (long)(3000L * Math.random());

        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "熔断测试";
    }
}
