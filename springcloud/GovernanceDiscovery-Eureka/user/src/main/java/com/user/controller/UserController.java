package com.user.controller;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import com.user.pojo.UserPo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.bind.annotation.*;

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
}
