package com.bird.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Author lipu
 * @Date 2021/8/16 11:20
 * @Description 高级客户端配置类
 */
@Configuration
@ConfigurationProperties(prefix = "elastic")
@Data
public class ElasticConfig {
    /**
     * ES集群地址
     */
    private List<String> ipList;

    /**
     * 初始化连接池大小
     */
    private Integer poolSize = 20;
}
