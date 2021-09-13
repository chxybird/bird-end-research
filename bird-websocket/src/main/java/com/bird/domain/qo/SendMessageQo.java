package com.bird.domain.qo;

import lombok.Data;

/**
 * @Author lipu
 * @Date 2021/9/13 13:22
 * @Description
 */
@Data
public class SendMessageQo {

    /**
     * 会话id
     */
    private String sessionId;
    /**
     * 推送的消息
     */
    private String message;
}
