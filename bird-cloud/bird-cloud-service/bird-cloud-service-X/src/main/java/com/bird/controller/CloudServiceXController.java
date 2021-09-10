package com.bird.controller;

import com.bird.rpc.RpcServiceA;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author lipu
 * @Date 2021/9/8 15:12
 * @Description
 */
@RestController
@RequestMapping("/cloud-x")
public class CloudServiceXController {
    @Resource
    private RpcServiceA rpcServiceA;

    @GetMapping("/get-info")
    public String getInfo(){
        return rpcServiceA.getInfo();
    }
}
