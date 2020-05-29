package springboot.learning.aop.aspectJ.service.impl;

import org.springframework.stereotype.Service;
import springboot.learning.aop.aspectJ.model.User;
import springboot.learning.aop.aspectJ.service.UserService;


/**
 * @author mrliz
 */

@Service
public class UserServiceImpl implements UserService {

    @Override
    public void printUser(User user) {
        if (user == null) { throw new RuntimeException("检查用户参数为空。........"); }

        System.out.println("id=" + user.getId());

        System.out.println("username = " + user.getUserName());

        System.out.println("note = " + user.getNote());
    }

    @Override
    public void manyAspects(){
        System.out.println("测试多个切面的顺序");
    }
}
