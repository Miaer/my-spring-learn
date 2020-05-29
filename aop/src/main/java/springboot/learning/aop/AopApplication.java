package springboot.learning.aop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springboot.learning.aop.aspectJ.aspect.MyAspect;
import springboot.learning.aop.aspectJ.aspect.MyAspect1;
import springboot.learning.aop.aspectJ.aspect.MyAspect2;
import springboot.learning.aop.aspectJ.aspect.MyAspect3;

/**
 * @author mrliz
 */
@SpringBootApplication(scanBasePackages = {"springboot.learning.aop.aspectJ"})
public class AopApplication {

    @Bean(name = "myAspect")
    public MyAspect initMyAspect(){
        return new MyAspect();
    }

    @Bean(name = "myAspect3")
    public MyAspect3 initMyAspect3(){
        return new MyAspect3();
    }

    @Bean(name = "myAspect1")
    public MyAspect1 initMyAspect1(){
        return new MyAspect1();
    }

    @Bean(name = "myAspect2")
    public MyAspect2 initMyAspect2(){
        return new MyAspect2();
    }

    public static void main(String[] args) {
        SpringApplication.run(AopApplication.class, args);
    }

}
