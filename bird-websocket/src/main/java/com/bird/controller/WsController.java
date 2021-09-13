package com.bird.controller;

import com.bird.domain.qo.SendMessageQo;
import com.bird.service.WsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lipu
 * @Date 2021/9/13 13:21
 * @Description
 */
@RestController
@RequestMapping("/ws")
public class WsController {

    /**
     * @Author lipu
     * @Date 2021/9/13 13:39
     * @Description 推送消息给前端
     */
    @PostMapping("/send-message")
    public String sendMessage(@RequestBody SendMessageQo sendMessageQo) throws Exception {
        WsService.sendOne(sendMessageQo.getSessionId(),sendMessageQo.getMessage());
        return "success";
    }

}
