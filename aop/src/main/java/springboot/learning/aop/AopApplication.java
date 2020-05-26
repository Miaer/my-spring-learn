package springboot.learning.aop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springboot.learning.aop.aspectJ.aspect.MyAspect;

@SpringBootApplication(scanBasePackages = {"springboot.learning.aop.aspectJ"})
public class AopApplication {

    @Bean(name = "myAspect")
    public MyAspect initMyAspect(){
        return new MyAspect();
    }

    public static void main(String[] args) {
        SpringApplication.run(AopApplication.class, args);
    }

}
