package com.bird.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author lipu
 * @Date 2021/8/26 10:22
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataVo {
    private String key;
    private Integer value;
}
