package com.mvc.controller;


import com.mvc.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 控制器通知
 * @author mrliz
 */
@ControllerAdvice(
        // 指定拦截包的控制器
        basePackages = {"com.mvc.controller.*"},
        // 限定被标注为@Controller或者RestController的类才被拦截
        annotations = {Controller.class, RestController.class})
public class VoControllerAdvice {
    // 异常处理，可以定义异常类型进行拦截处理
    @ExceptionHandler(value = NotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //定义为服务器错误状态码
    public Map<String, Object> exception(HttpServletRequest request,NotFoundException ex){
        Map<String, Object> msgMap = new HashMap<>();

        msgMap.put("code",ex.getCode());
        msgMap.put("message",ex.getCustomMsg());
        return msgMap;
    }

}
