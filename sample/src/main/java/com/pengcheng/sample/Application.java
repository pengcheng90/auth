package com.pengcheng.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: pengcheng
 * @date: 2020/7/4 14:56
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.pengcheng")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
