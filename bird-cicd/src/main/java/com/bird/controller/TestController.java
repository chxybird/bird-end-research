package com.bird.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lipu
 * @Date 2021/8/5 15:56
 * @Description
 */
@RestController
@RequestMapping("/test")
public class TestController {

    /**
     * @Author lipu
     * @Date 2021/8/5 15:57
     * @Description 获取信息
     */
    @GetMapping("/getInfo")
    public String getInfo(){
        return "info";
    }
}
