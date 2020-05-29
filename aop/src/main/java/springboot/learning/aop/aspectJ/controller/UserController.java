package springboot.learning.aop.aspectJ.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springboot.learning.aop.aspectJ.model.User;
import springboot.learning.aop.aspectJ.service.UserService;
import springboot.learning.aop.aspectJ.validator.UserValidator;

/**
 * @author mrliz
 */
@RequestMapping("/user")
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/print")
    @ResponseBody
    public User printUser(Long id,String userName,String note){
        User user = new User();

//        user.setId(id);
//        user.setUserName(userName);
//        user.setNote(note);

        // 如果为null，则执行afterthrowing方法
        userService.printUser(user);

        return user;
    }

    @RequestMapping("/vp")
    @ResponseBody
    public User validateAndPrint(Long id,String userName, String note){
        User user = new User();

        user.setId(id);
        user.setUserName(userName);
        user.setNote(note);

        UserValidator userValidator = (UserValidator) userService;

        if (userValidator.validate(user)){
            userService.printUser(user);
        }
        return user;
    }

    /**
     * 测试多个切面
     * @return
     */
    @RequestMapping("/manyAspects")
    @ResponseBody
    public String manyAspects(){
        userService.manyAspects();
        return "manyAspects";
    }
}
