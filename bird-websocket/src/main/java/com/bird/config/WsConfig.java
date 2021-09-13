package com.bird.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @Author lipu
 * @Date 2021/9/13 9:21
 * @Description ws配置类
 */
@Configuration
@EnableWebSocket
public class WsConfig {

    /**
     * @Author lipu
     * @Date 2020/7/31 11:36
     * @Description 开启@ServerEndpoint注解声明
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
