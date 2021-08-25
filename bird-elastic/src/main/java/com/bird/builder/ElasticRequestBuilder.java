package com.bird.builder;

import com.bird.common.*;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lipu
 * @Date 2021/8/11 18:45
 * @Description ES检索请求构造者
 */
public class ElasticRequestBuilder implements ElasticBuilder {


    /**
     * 被构造的通用请求对象
     */
    private final ElasticRequest request;
    /**
     * 排序对象
     */
    private final List<ElasticSort> sortList;

    public ElasticRequestBuilder() {
        this.sortList=new ArrayList<>(5);
        this.request = new ElasticRequest();
    }

    @Override
    public ElasticRequestBuilder buildWithQuery(QueryBuilder queryBuilder) {
        request.setQueryBuilder(queryBuilder);
        return this;
    }

    @Override
    public ElasticRequestBuilder buildWithPage(ElasticPage page) {
        request.setPage(page);
        return this;
    }

    @Override
    public ElasticRequest build() {
        return request;
    }

    @Override
    public ElasticRequestBuilder buildWithSort(ElasticSort sort) {
        sortList.add(sort);
        request.setSortList(this.sortList);
        return this;
    }

    @Override
    public ElasticRequestBuilder buildWithHighlight(ElasticHighlight highlight) {
        request.setHighlight(highlight);
        return this;
    }

    @Override
    public ElasticRequestBuilder buildWithIndex(ElasticIndex index) {
        request.setIndex(index);
        return this;
    }

    @Override
    public ElasticRequestBuilder buildWithDocument(ElasticDocument<?> document) {
        request.setDocument(document);
        return this;
    }

    @Override
    public ElasticRequestBuilder buildWithAggregation(AggregationBuilder aggregation) {
        request.setAggregation(aggregation);
        return this;
    }


}
