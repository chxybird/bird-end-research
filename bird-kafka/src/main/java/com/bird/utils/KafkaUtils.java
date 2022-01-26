package com.bird.utils;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * @Author lipu
 * @Date 2021/11/18 15:01
 * @Description kafka工具类
 */
public class KafkaUtils {

    private static final Properties PROPERTIES;
    /**
     * @Author lipu
     * @Date 2021/11/18 15:58
     * @Description kafka生产者消费者配置信息初始化
     */
    static {
        PROPERTIES = new Properties();
        //设置kafka集群地址
        PROPERTIES.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.206.137:9092,192.168.206.137:9093");
        //---生产者配置---
        //生产者KEY-VALUE序列化
        PROPERTIES.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        PROPERTIES.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        //配置消息ack机制 0(不需要确认) 1(分区的leader回复确认) -1(分区的leader+follower全部确认)
        PROPERTIES.put(ProducerConfig.ACKS_CONFIG, 1);
        //配置重试次数 当生产者在指定时间内没有收到kafka的ack就会进行重试
        PROPERTIES.put(ProducerConfig.RETRIES_CONFIG, 3);
        //重试时间间隔 3秒没有收到ack就重试
        PROPERTIES.put(ProducerConfig.RECONNECT_BACKOFF_MS_CONFIG, 3000);
        //配置生产者发送消息幂等性 开启幂等性
        PROPERTIES.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        //开启事务支持
        PROPERTIES.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG,"t-id");

        //配置缓冲区大小 默认32MB(33554432)
        PROPERTIES.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        //设置拉取线程一次批量拉取消息的最大大小 默认16kb(16384)
        PROPERTIES.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        //设置拉取线程批量拉取消息的时间间隔 10毫秒
        PROPERTIES.put(ProducerConfig.LINGER_MS_CONFIG, 10);
    }

    /**
     * @Author lipu
     * @Date 2021/11/18 16:24
     * @Description 创建生产者
     * 如需使用事务机制代码如下 示例如下
     * try {
     *     producer.beginTransaction();
     *     ---
     *     producer.commitTransaction();
     * } catch (Exception e) {
     *     producer.abortTransaction();
     *     producer.close();
     * }
     */
    public static Producer<String,String> getProducer(){
        //创建生产者
        KafkaProducer<String, String> producer = new KafkaProducer<>(PROPERTIES);
        //事务初始化
        producer.initTransactions();
        return producer;
    }

    /**
     * @Author lipu
     * @Date 2021/11/18 16:28
     * @Description 关闭生产者
     */
    public static void closeProducer(Producer<String,String> producer){
        producer.close();
    }


}
