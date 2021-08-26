package com.bird.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Author lipu
 * @Date 2021/8/26 10:00
 * @Description
 */
@Data
public class EsLog {

    /**
     * 用户名
     */
    private String username;
    /**
     * 操作级别
     */
    private String level;
    /**
     * 操作内容
     */
    private String content;
    /**
     * 操作模块
     */
    private String model;
    /**
     * 操作类型
     */
    private String type;
    /**
     * 操作时间
     */
    private Date logDate;

}
