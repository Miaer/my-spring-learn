package springboot.learning.aop.aspectJ.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;

/**
 * 切面1
 * @author mrliz
 */
@Aspect
@Order(1)
public class MyAspect1 {

    @Pointcut("execution(* springboot.learning.aop.aspectJ.service.impl.UserServiceImpl.manyAspects(..))")
    public void manyAspects(){}

    @Before("manyAspects()")
    public void before(){
        System.out.println("MyAspect1 before");
    }

    @After("manyAspects()")
    public void after(){
        System.out.println("MyAspect1 after");
    }

    @After("manyAspects()")
    public void afterReturning(){
        System.out.println("MyAspect1 afterReturning");
    }
}
