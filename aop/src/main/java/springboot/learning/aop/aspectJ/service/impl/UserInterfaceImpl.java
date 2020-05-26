package springboot.learning.aop.aspectJ.service.impl;

import org.springframework.stereotype.Service;
import springboot.learning.aop.aspectJ.model.User;
import springboot.learning.aop.aspectJ.service.UserInterface;


/**
 * @author mrliz
 */

@Service
public class UserInterfaceImpl implements UserInterface {

    @Override
    public void printUser(User user) {
        if (user == null) { throw new RuntimeException("检查用户参数为空。........"); }

        System.out.println("id=" + user.getId());

        System.out.println("username = " + user.getUserName());

        System.out.println("note = " + user.getNote());
    }
}
