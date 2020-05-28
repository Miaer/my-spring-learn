package springboot.learning.aop.aspectJ.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import springboot.learning.aop.aspectJ.validator.UserValidator;
import springboot.learning.aop.aspectJ.validator.impl.UserValidatorImpl;

/**
 * @author mrliz
 *
 * 切面：定义切点、各类通知和引入的内容的集合
 */
@Aspect
public class MyAspect {

    /**
     * 切点:向Spring描述哪些类的哪些方法需要启动AOP编程
     *
     * 每个注解上都有一个正则式，用来定义什么时候启用AOP——执行匹配到的方法执行时。
     * 每个注解都重复写了同一个正则式，显然比较冗余。为了解决这个问题，引入切点(pointCut)的概念。
     */
    @Pointcut("execution(* springboot.learning.aop.aspectJ.service.impl.UserServiceImpl.printUser(..))")
    public void pointCut(){}

    //@Before("execution(*springboot.learning.aop.aspectJ.service.impl.UserInterfaceImpl.printUser(..))")
    @Before("pointCut()")
    public void before(){
        System.out.println("before..........");
    }

    //@After("execution(*springboot.learning.aop.aspectJ.service.impl.UserInterfaceImpl.printUser(..))")
    @After("pointCut()")
    public void after(){
        System.out.println("after..........");
    }

    //@AfterReturning("execution(*springboot.learning.aop.aspectJ.service.impl.UserInterfaceImpl.printUser(..))")
    @AfterReturning("pointCut()")
    public void afterReturning(){
        System.out.println("afterReturning..........");
    }

    @AfterThrowing("pointCut()")
    public void afterThrowing(){
        System.out.println("afterThrowing..........");
    }

    @Around("pointCut()")
    public void around(ProceedingJoinPoint pjp) throws Throwable{
        System.out.println("around before............");

        // 回调原有方法
        pjp.proceed();

        System.out.println("around after.......");
    }

    /**
     * 增强UserService接口
     * DeclareParents:引入新的类来增强服务
     *  value：指向要增强的目标对象
     *  defaultImpl：引入增强功能的类，这里配置为UserValidatorImpl，用来校验用户是否为空的功能
     */
    @DeclareParents(value = "springboot.learning.aop.aspectJ.service.impl.UserServiceImpl+",
            defaultImpl = UserValidatorImpl.class)
    public UserValidator userValidator;
}
