package springboot.learning.aop.aspectJ.validator.impl;

import springboot.learning.aop.aspectJ.model.User;
import springboot.learning.aop.aspectJ.validator.UserValidator;

/**
 * 用户检测接口实现类
 */
public class UserValidatorImpl implements UserValidator {
    @Override
    public boolean validate(User user) {
        System.out.println("引入新的接口：" + UserValidator.class.getSimpleName());
        return user != null;
    }
}
