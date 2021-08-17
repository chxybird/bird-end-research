package com.bird.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author lipu
 * @Date 2021/8/11 19:56
 * @Description ES分页对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ElasticPage {
    /**
     * 页码
     */
    private Integer pageIndex;
    /**
     * 每页大小
     */
    private Integer pageSize;
}
