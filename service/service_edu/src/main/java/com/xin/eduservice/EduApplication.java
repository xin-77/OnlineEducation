package com.xin.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Xin
 * @date 2022/12/29 10:13
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.xin"})
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class, args);
    }
}