package springboot.learning.aop.aspectJ.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

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
}