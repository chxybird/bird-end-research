package com.bird.builder;

import com.bird.common.*;
import org.elasticsearch.index.query.QueryBuilder;

/**
 * @Author lipu
 * @Date 2021/8/11 19:00
 * @Description ES请求构造者接口
 */
public interface ElasticBuilder {
    /**
     * 构建对象
     * @return Es通用请求对象
     */
    ElasticRequest build();

    /**
     * 设置条件查询
     * @param queryBuilder 条件构造器
     * @return Es构造者对象
     */
    ElasticRequestBuilder buildWithQuery(QueryBuilder queryBuilder);

    /**
     * 设置分页条件
     * @param page 分页对象
     * @return Es构造者对象
     */
    ElasticRequestBuilder buildWithPage(ElasticPage page);

    /**
     * 设置排序对象
     * @param sort 排序对象
     * @return Es构造者对象
     */
    ElasticRequestBuilder buildWithSort(ElasticSort sort);

    /**
     * 设置高亮对象
     * @param highlight 高亮对象
     * @return Es构造者对象
     */
    ElasticRequestBuilder buildWithHighlight(ElasticHighlight highlight);

    /**
     * 设置索引库
     * @param index 索引库对象
     * @return Es构造者对象
     */
    ElasticRequestBuilder buildWithIndex(ElasticIndex index);

    /**
     * 设置索引库
     * @param document 文档对象
     * @return Es构造者对象
     */
    ElasticRequestBuilder buildWithDocument(ElasticDocument<?> document);



}
