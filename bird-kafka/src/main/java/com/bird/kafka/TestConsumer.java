package com.bird.kafka;

import com.bird.domain.bo.KafkaBO;
import com.bird.utils.JsonUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author lipu
 * @Date 2021/11/16 13:10
 * @Description kafka消费者
 */
@Component
public class TestConsumer {

    /**
     * 一个分区在同一时间只能被一个消费者的一个消费者消费,消费者不够则会执行re-balance(kafka分区与消费者的协调分配机制)。
     */

    /**
     * @Author lipu
     * @Date 2021/11/16 13:11
     * @Description 消费者 单条单条的处理
     */
    @KafkaListener(topics = "test")
    public void listenTest(ConsumerRecord<String, String> record, Acknowledgment ack) {
        //获取消息
        String value = record.value();
        System.out.println(value);
        //手动提交
        ack.acknowledge();
    }

    /**
     * @Author lipu
     * @Date 2021/11/16 16:24
     * @Description 指定消费者组、分区、批量消费
     */
    @KafkaListener(groupId = "default-group",topicPartitions = {@TopicPartition(topic = "test",partitions = {"1"})})
    public void listenTestCustomer(List<ConsumerRecord<String, String>> records, Acknowledgment ack) {
        //批量消费
        for (ConsumerRecord<String, String> record : records) {
            //获取消息
            String value = record.value();
            System.out.println(value);
        }
        //批量消费完毕后执行手动提交
        ack.acknowledge();
    }

    /**
     * @Author lipu
     * @Date 2021/11/16 16:38
     * @Description 测试新的消费者介入
     */
    @KafkaListener(topics = "test",groupId = "new")
    public void listenTestAdd(ConsumerRecord<String, String> record, Acknowledgment ack) {
        //获取消息
        String value = record.value();
        System.out.println(value);
        //手动提交
        ack.acknowledge();
    }

}
