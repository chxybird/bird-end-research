package com.bird.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author lipu
 * @Date 2021/8/12 9:10
 * @Description Es高亮对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ElasticHighlight {
    /**
     * 高亮字段
     */
    private String field;
    /**
     * 高亮修饰前缀
     */
    private String preTag;
    /**
     * 高亮修饰后缀
     */
    private String suffixTag;
}
