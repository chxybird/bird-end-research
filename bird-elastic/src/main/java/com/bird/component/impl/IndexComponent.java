package com.bird.component.impl;

import com.bird.common.ElasticIndex;
import com.bird.common.ElasticRequest;
import com.bird.component.intf.IIndexComponent;
import com.bird.exception.ElasticException;
import com.bird.factory.ElasticClientFactory;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @Author lipu
 * @Date 2021/8/13 11:18
 * @Description
 */
@Slf4j
@Component
public class IndexComponent implements IIndexComponent {

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
     * @Date 2021/5/8 15:02
     * @Description 创建索引库
     */
    public void createIndex(ElasticRequest request) {
        ElasticIndex index = request.getIndex();
        if (index == null || index.getIndexName() == null) {
            log.info("索引信息为空,创建索引失败");
            return;
        }
        try {
            RestHighLevelClient client = getClient();
            //构建请求
            CreateIndexRequest createIndexRequest = new CreateIndexRequest(index.getIndexName());
            //创建默认Settings 设置分片数和副本数
            createIndexRequest.settings(Settings.builder()
                    .put("index.number_of_shards", index.getShards() != null ? index.getShards() : 5)
                    .put("index.number_of_replicas", index.getReplicas() != null ? index.getReplicas() : 1)
            );
            //创建默认mappings 相当于定义表结构
            if (index.getMappings() != null) {
                createIndexRequest.mapping(index.getMappings());
            }
            //设置索引库别名
            if (index.getAlias() != null) {
                createIndexRequest.alias(new Alias(index.getAlias()));
            }
            //发送请求 创建索引
            client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
            revertClient(client);
        } catch (Exception e) {
            log.error("索引创建失败");
            throw new ElasticException("索引创建失败");

        }
    }

    /**
     * @Author lipu
     * @Date 2021/8/13 13:28
     * @Description 判断索引库是否存在
     */
    public boolean existIndex(ElasticRequest request) {
        ElasticIndex index = request.getIndex();
        if (index == null || index.getIndexName() == null) {
            log.info("索引请求信息缺少,无法判断索引库状态");
            return false;
        }
        try {
            RestHighLevelClient client = getClient();
            GetIndexRequest getIndexRequest = new GetIndexRequest(index.getIndexName());
            boolean exists = client.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
            revertClient(client);
            return exists;
        } catch (Exception e) {
            log.error("无法判断索引库状态");
            throw new ElasticException("无法判断索引库状态");
        }
    }
}
