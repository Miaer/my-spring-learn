本次AOP学习，参考的是杨开振老师的《深入浅出Spring Boot 2.X》一书第4章内容。

“约定是本章的核心，也是Spring AOP的本质”。

"Spring AOP 通过与我们的约定，把对应的方法通过动态代理技术织入约定的流程中，这就是Spring AOP编程的本质。"


___________________________________________________________________________________________________________________________________________________________________________________________________________________________________
Spring AOP是一种基于方法的AOP，只能应用于方法上。

术语：
====
连接点(join point)：对应的是具体被拦截的对象，因为Spring只能支持方法，所以被拦截的对象往往是指特定的方法。HelloServiceImpl的sayHello方法就是一个连接点。AOP通过动态代理的技术把它织入对应的流程中去。

切点(point cut)：有时候，我们的切面不单单应用于单个方法，也可能是多个类的不同方法，这时，可以通过正则式和指示器的规则去定义。切点就是提供这样功能的一个概念。

通知(advice)：就是安装约定的流程下的方法，分为前置通知(before advice)、后置通知(after advice)、环绕通知(around advice)、事后返回通知(afterReturning)和异常通知(afterThrowing advice)，它会根据约定织入流程中，需要弄明白他们在流程中的顺序和运行的条件。

目标对象(target)：即被代理对象，例如，HelloServiceImpl实例就是一个目标对象，它被代理了。

引入(introduction)：是指引入新的类和方法。

织入(weaving)： 它是一个通过动态代理技术，为原有服务对象生成代理对象，然后将与切点定义匹配的连接点拦截，并按约定将各类通知织入约定的流程的过程。

切面(aspect)：是一个可以定义切点、各类通知和引入的内容，Spring AOP将通过它的信息来增强Bean的功能或者将对应的方法织入流程。

以上描述还是比较抽象的，参考AOP图解.png


![image](/photo/AOP图解.png)
___________________________________________________________________________________________________________________________________________________________________________________________________________________________________

AspectJ 注解开发
====
因为Spring AOP只能对方法进行拦截，首先要确定的就是拦截什么方法，让它能织入约定的流程中。

确定连接点
--
    任何AOP编程，首先确定的是在什么地方需要AOP，也就是需要确定连接点(什么类什么方法)的问题。
    
在aspectJ包中，现有一个UserService接口及其实现类UserServiceImpl。下面将以UserServiceImpl中printUser方法为连接点，进行AOP编程。


正则匹配参数解释
--
execution(* springboot.learning.aop.aspectJ.service.impl.UserInterfaceImpl.printUser(..))

    execution: 表示在执行的时候，拦截里面的正则匹配的方法
    * :表示任意返回类型的方法
    springboot.learning.aop.aspectJ.service.impl.UserInterfaceImpl:指定目标对象的全限定名称
    printUser：指定目标对象的方法
    (..)：表示任意参数进行匹配

指定类的方法进入AOP流程
--
execution(* springboot.learning.aop.aspectJ.service.impl.UserInterfaceImpl.printUser(..) && bean('userServiceImpl'))

上述表达式中，"&&"表示并且，bean中定义的字符串为Spring bean名称的限定
    
环绕通知
--
环绕通知会在目标方法执行之前执行 ，会在目标方法结束之前结束

引入
--
校验用户信息为空，抛出异常。

通知获取参数
--
能够在AOP的任何地方获取用户的参数。

织入
--
织入是一个生成动态代理对象并且将切面和目标对象方法编织成为约定流程的过程。
动态代理有多种实现方式，本次案例都是使用的接口+实现类的模式，这也是spring推荐的方式，还有CGLIB、javassist、ASM等。
对JDK而言，被代理对象必须拥有接口，CGLIB不做要求，默认情况下，Spring会按照这样的一条规则处理：
    当你需要使用AOP的类拥有接口时，它会以JDK动态代理运行，否则以CGLIB运行。
    
多个切面
--
Spring支持多个切面运行，在组织多个切面时，我们需要知道其运行顺序。
首先创建三个切面类：
    MyAspect1,MyAspect2,MyAspect3

执行顺序
---
bean的执行顺序
--
多个切面bean的执行顺序，在本次版本中，推测默认执行顺序与bean的加载顺序相同，但是这种是不稳定的
使用Order注解，标注在切面类上，来规定切面的执行顺序
    
    @Order(1)
    @Order(2)
    @Order(3)

通知的执行顺序
--
测试的执行结果如下所示：

    MyAspect1 before
    MyAspect2 before
    MyAspect3 before
    测试多个切面的顺序
    MyAspect3 after
    MyAspect3 afterReturning
    MyAspect2 after
    MyAspect2 afterReturning
    MyAspect1 after
    MyAspect1 afterReturning
可以看到前置通知(before)都是从小到大运行，后置通知(after)都是从大到小运行，这是典型的责任链模式的顺序。
