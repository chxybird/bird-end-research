package com.bird.domain;

import lombok.Data;

/**
 * @Author lipu
 * @Date 2021/9/11 14:07
 * @Description
 */
@Data
public class TaskQo {
    /**
     * 任务名称 唯一
     */
    private String taskName;
    /**
     * Cron表达式
     */
    private String cron;
}
