package com.bird.common;

import lombok.Data;
import org.elasticsearch.index.query.QueryBuilder;

import java.util.List;

/**
 * @Author lipu
 * @Date 2021/8/11 18:40
 * @Description ES检索请求对象
 */
@Data
public class ElasticRequest {
    /**
     * 搜索的索引库名称
     */
    private ElasticIndex index;
    /**
     * 分页对象
     */
    private ElasticPage page;
    /**
     * 高亮对象
     */
    private ElasticHighlight highlight;
    /**
     * 条件构造器
     */
    private QueryBuilder queryBuilder;
    /**
     * 排序构造器
     */
    private List<ElasticSort> sortList;

    /**
     * 文档对象
     */
    private ElasticDocument<?> document;

}
