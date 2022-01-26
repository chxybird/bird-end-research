package com.bird.entity.prom;

import lombok.Data;

import java.util.List;

/**
 * @Author lipu
 * @Date 2021/11/2 15:51
 * @Description
 */
@Data
public class PromData {
    /**
     * 返回值类型
     */
    private String resultType;
    /**
     * 根据返回的类型不同,其Object的结构也不同
     */
    private Object result;
}
