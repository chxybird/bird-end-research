package com.bird.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author lipu
 * @Date 2021/8/11 19:57
 * @Description ES排序对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElasticSort {
    /**
     * 排序字段
     */
    private String field;

    /**
     * 是否升序 true升序 false降序
     */
    private Boolean isAsc;
}
