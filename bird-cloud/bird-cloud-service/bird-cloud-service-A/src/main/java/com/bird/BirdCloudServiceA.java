package com.bird;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Author lipu
 * @Date 2021/9/8 10:27
 * @Description
 */
@SpringBootApplication
@EnableEurekaClient
public class BirdCloudServiceA {
    public static void main(String[] args) {
        SpringApplication.run(BirdCloudServiceA.class);
    }
}
