package com.bird.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

/**
 * @Author lipu
 * @Date 2021/5/25 10:46
 * @Description
 */
@RestController
public class StudentController {

    @GetMapping("/get-info")
    public String getInfo() throws Exception {
        TimeUnit.SECONDS.sleep(10000);
        return "success";
    }

    /**
     * @Author lipu
     * @Date 2021/9/14 10:58
     * @Description 异步方式
     */
    @GetMapping("get-mono")
    public Mono<String> getMono() throws Exception {
        TimeUnit.SECONDS.sleep(10000);
        return Mono.just("success");
    }
}
