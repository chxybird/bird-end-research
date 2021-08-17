package com.bird.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author lipu
 * @Date 2021/8/17 13:55
 * @Description 文档对象
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ElasticDocument<T> {

    /**
     * 单数据
     */
    private T data;

    /**
     * 多数据
     */
    private List<T> dataList;


    public ElasticDocument<T> data(T data) {
        this.data = data;
        return this;
    }

    public ElasticDocument<T> dataList(List<T> dataList) {
        this.dataList = dataList;
        return this;
    }

}
