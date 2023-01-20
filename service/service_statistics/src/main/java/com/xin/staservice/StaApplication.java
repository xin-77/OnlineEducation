package com.xin.staservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author xin
 * @since 2023/1/20 12:27
 */

@SpringBootApplication
@ComponentScan(basePackages = {"com.xin"})
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling
@MapperScan("com.xin.staservice.mapper")
public class StaApplication {
    public static void main(String[] args) {
        SpringApplication.run(StaApplication.class, args);
    }
}
