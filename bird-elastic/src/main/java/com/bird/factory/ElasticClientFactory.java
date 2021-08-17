package com.bird.factory;

import com.bird.config.ElasticConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author lipu
 * @Date 2021/8/11 18:24
 * @Description 高级客户端工厂类
 */
@Component
@Slf4j
public final class ElasticClientFactory {

    /**
     * 配置类信息
     */
    @Resource
    private ElasticConfig config;
    /**
     * 客户端连接池 阻塞队列线程安全
     */
    private LinkedBlockingQueue<RestHighLevelClient> pool;




    /**
     * @Author lipu
     * @Date 2021/8/11 18:26
     * @Description 初始化客户端
     */
    @PostConstruct
    private void initClient(){
        try{
            //加载集群配置信息 创建高级客户端对象
            List<String> ipList = config.getIpList();
            List<HttpHost> hostList = new ArrayList<>();
            ipList.forEach((ip) -> {
                HttpHost httpHost = new HttpHost(ip, 9200, "http");
                hostList.add(httpHost);
            });
            HttpHost[] hosts = new HttpHost[hostList.size()];
            hostList.toArray(hosts);
            RestClientBuilder builder = RestClient.builder(hosts);
            //初始化连接池
            pool=new LinkedBlockingQueue<>(config.getPoolSize());
            for (int i = 0; i < config.getPoolSize(); i++) {
                pool.put(new RestHighLevelClient(builder));
            }
        }catch (Exception e){
            log.error("ES连接池初始化失败!!!");
        }
    }

    /**
     * @Author lipu
     * @Date 2021/8/11 18:37
     * @Description 获取高级客户端
     */
    public RestHighLevelClient getClient(){
        try {
            return pool.take();
        } catch (InterruptedException e) {
            log.error("从线程池获取ES高级客户端失败");
            return null;
        }
    }

    /**
     * @Author lipu
     * @Date 2021/8/13 14:50
     * @Description 获取配置信息
     */
    public ElasticConfig getConfig(){
        return this.config;
    }

    /**
     * @Author lipu
     * @Date 2021/8/16 15:28
     * @Description 获取连接池
     */
    public LinkedBlockingQueue<RestHighLevelClient> getPool(){
        return this.pool;
    }

}
