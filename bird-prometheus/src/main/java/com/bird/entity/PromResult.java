package com.bird.entity;

import com.bird.entity.prom.PromData;
import lombok.Data;

/**
 * @Author lipu
 * @Date 2021/11/2 15:10
 * @Description
 */
@Data
public class PromResult {
    /**
     * 响应状态 "success" | ”error“
     */
    private String status;
    private PromData data;
    //下面两个字段只会在status为error的时候才有
    private String errorType;
    private String error;
}
