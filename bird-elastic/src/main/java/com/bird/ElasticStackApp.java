package com.bird;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @Author lipu
 * @Date 2021/5/8 14:15
 * @Description
 */
@SpringBootApplication
@EnableConfigurationProperties
public class ElasticStackApp {
    public static void main(String[] args) {
        SpringApplication.run(ElasticStackApp.class);
    }
}
