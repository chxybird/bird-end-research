package com.bird.controller;

import com.bird.domain.bo.KafkaBO;
import com.bird.utils.JsonUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author lipu
 * @Date 2021/11/16 11:09
 * @Description
 */
@RestController
@RequestMapping("/kafka")
public class KafkaController {

    @Resource
    private KafkaTemplate<String,Object> kafkaTemplate;

    /**
     * @Author lipu
     * @Date 2021/11/16 11:15
     * @Description 发送消息
     */
    @GetMapping("/send")
    public String sendMsg(){
        KafkaBO kafkaBo=new KafkaBO();
        kafkaBo.setId(1L);
        kafkaBo.setMsg("阿尔弗雷德");
        String json = JsonUtils.objectToJson(kafkaBo);
        kafkaTemplate.send("test","user",json);
        return "success";
    }
}
