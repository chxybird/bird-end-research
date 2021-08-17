package com.bird.component.impl;

import com.bird.common.ElasticIndex;
import com.bird.common.ElasticRequest;
import com.bird.component.intf.IDocumentComponent;
import com.bird.exception.ElasticException;
import com.bird.factory.ElasticClientFactory;
import com.bird.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author lipu
 * @Date 2021/8/11 18:39
 * @Description ES组件
 */
@Component
@Slf4j
public class DocumentComponent implements IDocumentComponent {

    @Resource
    private ElasticClientFactory factory;

    /**
     * @Author lipu
     * @Date 2021/8/17 14:06
     * @Description 线程池获取对象
     */
    private RestHighLevelClient getClient(){
        return factory.getClient();
    }

    /**
     * @Author lipu
     * @Date 2021/8/17 14:08
     * @Description 归还客户端对象到线程池
     */
    private void revertClient(RestHighLevelClient client) throws Exception {
        factory.getPool().put(client);
    }

    /**
     * @Author lipu
     * @Date 2021/8/11 18:44
     * @Description ES文档检索
     */
    @Override
    public <T> List<T> search(ElasticRequest request, Class<T> clazz) {
        if (request == null) {
            log.info("request不能为空");
            return null;
        }
        try {
            RestHighLevelClient client=getClient();
            //索引处理
            SearchRequest searchRequest = dealIndex(request);
            SearchSourceBuilder builder = new SearchSourceBuilder();
            //条件处理
            dealQuery(builder, request);
            //分页处理
            dealPage(builder, request);
            //排序处理
            dealSort(builder, request);
            //高亮处理
            dealHighlight(builder, request);
            //执行检索
            searchRequest.source(builder);
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            //结果处理
            return dealResult(searchResponse, request, clazz, client);
        } catch (Exception e) {
            log.error("ES文档检索异常");
            throw new ElasticException("ES文档检索异常");
        }
    }

    /**
     * @Author lipu
     * @Date 2021/8/13 14:05
     * @Description ES文档入库
     */
    public void insert(ElasticRequest request) {
        ElasticIndex index = request.getIndex();
        if (index == null || index.getIndexName() == null) {
            log.info("索引请求参数为空,ES文档入库失败");
            return;
        }
        if (request.getDocument().getData() == null) {
            log.info("文档为空,跳过入库操作");
            return;
        }
        try {
            RestHighLevelClient client = getClient();
            IndexRequest indexRequest = new IndexRequest();
            indexRequest.index(index.getIndexName());
            String json = JsonUtils.entityToJson(request.getDocument().getData());
            indexRequest.source(json, XContentType.JSON);
            client.index(indexRequest, RequestOptions.DEFAULT);
            revertClient(client);
        } catch (Exception e) {
            log.error("发生异常,ES文档入库失败");
            throw new ElasticException("发生异常,ES文档入库失败");
        }
    }

    /**
     * @Author lipu
     * @Date 2021/8/17 11:09
     * @Description ES文档批量入库
     */
    public void insertList(ElasticRequest request) {
        ElasticIndex index = request.getIndex();
        if (index == null || index.getIndexName() == null) {
            log.info("索引请求参数为空,ES文档入库失败");
            return;
        }
        if (request.getDocument().getDataList() == null) {
            log.info("文档为空,跳过入库操作");
            return;
        }
        try {
            RestHighLevelClient client = getClient();
            List<?> dataList = request.getDocument().getDataList();
            //批量操作
            BulkRequest bulkRequest = new BulkRequest();
            dataList.forEach(item->{
                IndexRequest indexRequest = new IndexRequest();
                indexRequest.index(index.getIndexName());
                String json = JsonUtils.entityToJson(request.getDocument().getData());
                indexRequest.source(json, XContentType.JSON);
                bulkRequest.add(indexRequest);
            });
            client.bulk(bulkRequest, RequestOptions.DEFAULT);
            revertClient(client);
        } catch (Exception e) {
            log.error("发生异常,ES文档入库失败");
            throw new ElasticException("发生异常,ES文档入库失败");
        }
    }

    private void dealPage(SearchSourceBuilder builder, ElasticRequest request) {
        if (request.getPage() != null) {
            builder.from(request.getPage().getPageIndex() - 1);
            builder.size(request.getPage().getPageSize());
        } else {
            builder.from(0);
            builder.size(50);
        }
    }

    private void dealSort(SearchSourceBuilder builder, ElasticRequest request) {
        if (request.getSortList() != null && request.getSortList().size() > 0) {
            request.getSortList().forEach(sort -> {
                builder.sort(new FieldSortBuilder(sort.getField())
                        .order(sort.getIsAsc() ? SortOrder.ASC : SortOrder.DESC));
            });
        } else {
            //相关性_source降序排序
            builder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
        }
    }

    private void dealQuery(SearchSourceBuilder builder, ElasticRequest request) {
        if (request.getQueryBuilder() != null) {
            builder.query(request.getQueryBuilder());
        }
    }

    private void dealHighlight(SearchSourceBuilder builder, ElasticRequest request) {
        if (request.getHighlight() != null) {
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.fields().add(new HighlightBuilder.Field(request.getHighlight().getField()));
            highlightBuilder.preTags(request.getHighlight().getPreTag());
            highlightBuilder.postTags(request.getHighlight().getSuffixTag());
            //允许多field高亮
            highlightBuilder.requireFieldMatch(false);
            //最大高亮分片数 防止震荡
            highlightBuilder.fragmentSize(800000);
            //从第一个分片获取高亮片段
            highlightBuilder.numOfFragments(0);
            builder.highlighter(highlightBuilder);
        }
    }

    private SearchRequest dealIndex(ElasticRequest request) {
        SearchRequest searchRequest;
        if (request.getIndex() != null) {
            searchRequest = new SearchRequest(request.getIndex().getIndexName());
        } else {
            searchRequest = new SearchRequest("*");
        }
        return searchRequest;
    }

    private <T> List<T> dealResult(SearchResponse searchResponse,
                                   ElasticRequest request,
                                   Class<T> clazz,
                                   RestHighLevelClient client) throws Exception {
        //结果集处理
        SearchHit[] searchHits = searchResponse.getHits().getHits();
        List<T> dataList = new ArrayList<>();
        for (SearchHit searchHit : searchHits) {
            String jsonData = searchHit.getSourceAsString();
            T entity = JsonUtils.jsonToObject(jsonData, clazz);
            //高亮处理
            if (request.getHighlight() != null) {
                Map<String, HighlightField> mapInfo = searchHit.getHighlightFields();
                HighlightField highlightField = mapInfo.get(request.getHighlight().getField());
                Text[] fragments = highlightField.getFragments();
                StringBuilder stringBuilder = new StringBuilder();
                for (Text text : fragments) {
                    stringBuilder.append(text.toString());
                }
                //高亮结果 通过反射替换entity指定的属性值(高亮结果替换)
                String highContent = stringBuilder.toString();
                Field declaredField = entity.getClass().getDeclaredField(request.getHighlight().getField());
                declaredField.setAccessible(true);
                //只有是字符串类型才处理高亮
                if (declaredField.getType() == String.class) {
                    declaredField.set(entity, highContent);
                }
            }
            dataList.add(entity);
        }
        //归还连接
        revertClient(client);
        return dataList;
    }
}
