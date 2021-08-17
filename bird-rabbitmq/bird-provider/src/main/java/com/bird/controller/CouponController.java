package com.bird.controller;

import com.bird.config.BirdConfirmCallback;
import com.bird.config.BirdReturnCallback;
import com.bird.config.mq.DirectConfig;
import com.bird.entity.Coupon;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author lipu
 * @Date 2021/5/6 14:10
 * @Description
 */
@RestController
@RequestMapping("/coupon")
public class CouponController {
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private BirdConfirmCallback birdConfirmCallback;
    @Resource
    private BirdReturnCallback birdReturnCallback;

    /**
     * @Author lipu
     * @Date 2021/5/6 14:11
     * @Description 抢券 模拟1000并发
     */
    @PostMapping("/scramble")
    public String scramble(){
        Coupon coupon=new Coupon();
        coupon.setId(1L);
        rabbitTemplate.convertAndSend("bird-coupon","scramble",coupon);
        return "success";
    }
    
    /**
     * @Author lipu
     * @Date 2021/6/7 18:06
     * @Description 可靠消息投递
     */
    @PostMapping("/ack")
    public String ack(){
        //设置可靠消息投递
        rabbitTemplate.setConfirmCallback(birdConfirmCallback);
        rabbitTemplate.setReturnCallback(birdReturnCallback);
        //发送消息
        rabbitTemplate.convertAndSend(DirectConfig.EXCHANGE_DIRECT,DirectConfig.KEY_QUEUE_ONE,"可靠消息投递");
        return "success";
    }

    /**
     * @Author lipu
     * @Date 2021/7/2 21:59
     * @Description 发送消息
     */
    @PostMapping("/test")
    public String test(){
        rabbitTemplate.convertAndSend(DirectConfig.EXCHANGE_DIRECT,DirectConfig.KEY_QUEUE_ONE,"普通消息");
        return "success";
    }
}
