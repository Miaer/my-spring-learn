package springboot.learning.aop.aspectJ.service;

import springboot.learning.aop.aspectJ.model.User;

/**
 * @author mrliz
 *
 * AspectJ 注解AOP编程接口——切面
 */
public interface UserService {

    void printUser(User user);

    void manyAspects();
}
