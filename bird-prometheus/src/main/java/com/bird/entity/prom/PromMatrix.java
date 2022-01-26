package com.bird.entity.prom;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Author lipu
 * @Date 2021/11/2 15:25
 * @Description
 */
@Data
public class PromMatrix{
    private Map<String,String> metric;
    private List<List<Object>> values;
}
