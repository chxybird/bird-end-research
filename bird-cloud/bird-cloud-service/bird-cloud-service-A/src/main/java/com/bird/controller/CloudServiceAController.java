package com.bird.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lipu
 * @Date 2021/9/8 11:04
 * @Description
 */
@RestController
@RequestMapping("/cloud-a")
public class CloudServiceAController {

    /**
     * @Author lipu
     * @Date 2021/9/8 11:04
     * @Description 测试接口
     */
    @GetMapping("/get-info")
    public String getInfo(){
        return "cloud-A is success";
    }

}
