package com.bird.entity.prom;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Author lipu
 * @Date 2021/11/3 15:00
 * @Description
 */
@Data
public class PromVector {
    private Map<String,String> metric;
    private List<List<String>> values;
}
