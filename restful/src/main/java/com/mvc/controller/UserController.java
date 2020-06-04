package com.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * @author mrliz
 */
//@Controller
@RestController // 默认所有的方法都将返回JSON
public class UserController {

    @Autowired
    private UserService userService = null;

    @GetMapping("")
    public UserVo getUser(@PathVariable("id") Long id){
        User user = userService.getUser(id);
        return changeToVo(user);
    }

    public ResponseEntity insertUserEntity(@RequestBody UserVo userVo){
        // do smthing
        boolean result = false;
        HttpHeaders headers = new HttpHeaders();
        String success = (result == null || result.getId == null) ? "false" : "true";

        // 设置响应头 常用方式
        headers.add("success",success);
        //使用List方式，不是太常用
        //headers.put("success", Arrays.asList(success));

        // 返回创建成功的状态码
        return new ResponseEntity(result,headers, HttpStatus.CREATED);
        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 指定状态码为201
    public UserVo insertUserAnnotation(@RequestBody UserVo userVo){
        User user = this.changeToPo(userVo);
        userService.insertUser(user);
        UserVo result = this.changeToVo(user);
        return result;
    }
}
