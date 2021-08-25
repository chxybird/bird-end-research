package com.bird.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @Author lipu
 * @Date 2021/8/13 11:01
 * @Description Es索引库对象
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ElasticIndex {
    /**
     * 索引库名称
     */
    private String indexName;
    /**
     * 分片数
     */
    private Integer shards;
    /**
     * 副本数
     */
    private Integer replicas;
    /**
     * 索引别名
     */
    private String alias;
    /**
     * 索引映射信息
     */
    private Map<String,Object> mappings;

    public ElasticIndex indexName(String indexName){
        this.indexName=indexName;
        return this;
    }

    public ElasticIndex shards(Integer shards){
        this.shards=shards;
        return this;
    }

    public ElasticIndex replicas(Integer replicas){
        this.replicas=replicas;
        return this;
    }

    public ElasticIndex alias(String alias){
        this.alias=alias;
        return this;
    }

    public ElasticIndex mappings(Map<String,Object> mappings){
        this.mappings=mappings;
        return this;
    }

}
