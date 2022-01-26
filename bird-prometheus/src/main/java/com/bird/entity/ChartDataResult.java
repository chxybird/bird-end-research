package com.bird.entity;

import lombok.Data;

/**
 * @Author lipu
 * @Date 2021/11/2 16:50
 * @Description 报表数据通用结果集
 */
@Data
public class ChartDataResult {
    /**
     * X轴 key 一般就是时间
     */
    private String key;
    /**
     * Y轴 值
     */
    private String value;
    /**
     * 分组 折线图可以展示多条统计数据
     */
    private String group;
    /**
     * 单位
     */
    private String unit;
}
