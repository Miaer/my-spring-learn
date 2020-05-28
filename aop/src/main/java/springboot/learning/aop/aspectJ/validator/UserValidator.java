package springboot.learning.aop.aspectJ.validator;

import springboot.learning.aop.aspectJ.model.User;

/**
 * 用户检测接口
 * @author mrliz
 */
public interface UserValidator {

    // 检测用户对象是否为空
    boolean  validate(User user);
}
