package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author mrliz
 */
@SpringBootApplication(scanBasePackages = "com")
// 启用Feign
@EnableFeignClients(basePackages = "com")
// 启动断路器
@EnableCircuitBreaker
public class ProductApplication {


    /**
     * @LoadBalanced 让RestTemplate实现负载均衡，通过这个RestTemplate对象调用用户微服务请求的时候，Ribbon会自动给用户微服务节点
     * 实现负载均衡，这样请求就会分摊到微服务的各个节点，降低单点的压力。
     */
    @LoadBalanced   // 多节点负载均衡
    @Bean(name = "restTemplate")
    public RestTemplate initRestTemplate(){
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }

}
