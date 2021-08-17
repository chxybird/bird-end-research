package com.bird.component.intf;

import com.bird.common.ElasticRequest;
import org.elasticsearch.client.RestHighLevelClient;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author lipu
 * @Date 2021/8/11 19:49
 * @Description 文档组件
 */
public interface IDocumentComponent extends ElasticComponent {

    /**
     * 搜索文档
     * @param request es请求
     * @param clazz 转换的实体类型
     * @param <T> 泛型
     * @return List集合
     */
    <T> List<T> search(ElasticRequest request, Class<T> clazz);

}
