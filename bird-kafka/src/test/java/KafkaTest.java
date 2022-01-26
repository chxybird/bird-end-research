import com.bird.KafkaApp;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @Author lipu
 * @Date 2021/11/10 16:07
 * @Description
 */
@SpringBootTest(classes = KafkaApp.class)
@Slf4j
public class KafkaTest {

    /**
     * @Author lipu
     * @Date 2021/11/10 16:07
     * @Description 生产者发送消息快速入门
     */
    @Test
    void sendMsg() throws ExecutionException, InterruptedException {
        //设置配置信息
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.206.137:9092,192.168.206.137:9093");
        //KEY-VALUE序列化
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        //创建生产者
        Producer<String, String> producer = new KafkaProducer<>(properties);
        //设置消息 不设置分区则根据hash算法选择分区 hash(key)%partitionNum
        ProducerRecord<String, String> record = new ProducerRecord<>("test", "1", "张三");
        //发送消息 同步发送消息
        RecordMetadata metadata = producer.send(record).get();
        log.info("发送到主题为:{},发送的分区为{},偏移量为{}", metadata.topic(), metadata.partition(), metadata.offset());
    }

    /**
     * @Author lipu
     * @Date 2021/11/10 19:29
     * @Description 同步发送和异步发送
     */
    @Test
    void SyncAndAsync() throws Exception {
        //设置配置信息
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.206.137:9092,192.168.206.137:9093");
        //KEY-VALUE序列化
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        //创建生产者
        Producer<String, String> producer = new KafkaProducer<>(properties);
        //设置消息 不设置分区则根据hash算法选择分区 hash(key)%partitionNum
        ProducerRecord<String, String> record = new ProducerRecord<>("test", "1", "张三");
        //发送消息 异步发送消息
        Future<RecordMetadata> future = producer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                //发生异常
                if (e != null) {
                    log.error("消息发送失败");
                    //可以记录丢失的消息的日志信息
                }
                log.info("发送到主题为:{},发送的分区为{},偏移量为{}", recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset());
            }
        });
        //主线程睡眠
        TimeUnit.SECONDS.sleep(30);
    }

    /**
     * @Author lipu
     * @Date 2021/11/10 20:19
     * @Description 同步发送ack机制 同步发送是前提
     */
    @Test
    void ack() throws Exception {
        /**
         * 生产者发送消息到kafka、kafka会返回ack,生产者在此期间会一直阻塞等待ack,阻塞时间超过3秒会进行重试,重试次数为3次。
         * 关于ack的配置参数
         * 0:       生产者发送消息到leader分区,leader接收到请求后不需要确认消息是否持久化到磁盘就返回ack。
         * 1:       默认值,生产者送消息到leader分区,leader接收到请求后需要确认消息是否持久化到磁盘后再返回ack。
         * all(-1): 生产者发送消息到leader分区,leader接受到消息后需要确认ISR中所有的副本都持久化磁盘后再返回ack。
         */

        //设置配置信息
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.206.137:9092,192.168.206.137:9093");
        //KEY-VALUE序列化
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        //配置消息ack机制
        properties.put(ProducerConfig.ACKS_CONFIG, 1);
        //配置重试次数
        properties.put(ProducerConfig.RETRIES_CONFIG, 3);
        //重试时间间隔 300毫秒 0.3秒 0.3秒没有收到ack就重试
        properties.put(ProducerConfig.RECONNECT_BACKOFF_MS_CONFIG, 300);
        //创建生产者
        Producer<String, String> producer = new KafkaProducer<>(properties);
        //设置消息 不设置分区则根据hash算法选择分区 hash(key)%partitionNum
        ProducerRecord<String, String> record = new ProducerRecord<>("test", "1", "张三");
        //发送消息 同步发送消息
        RecordMetadata metadata = producer.send(record).get();
        log.info("发送到主题为:{},发送的分区为{},偏移量为{}", metadata.topic(), metadata.partition(), metadata.offset());
    }

    /**
     * @Author lipu
     * @Date 2021/11/11 14:26
     * @Description 缓冲机制
     */
    @Test
    void cache() throws Exception {
        /**
         * kafka在发送消息的时候为了提高性能有缓存批量机制
         * 生产者将消息发送到缓冲池中,kafka有一个默认的线程从缓冲池中拉取数据。一次拉取最多16kb数据。
         * 如果拉取满了16kb的消息数据就将消息发送给broker,如果没有拉取满16KB,根据配置的时间间隔也将数据发送到broker。
         */
        //设置配置信息
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.206.137:9092,192.168.206.137:9093");
        //KEY-VALUE序列化
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        //配置缓冲区大小 默认32MB(33554432)
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        //设置一次批量拉取消息的最大大小 默认16kb(16384)
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        //设置批量拉取消息的时间间隔 10毫秒
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 10);
        //创建生产者
        Producer<String, String> producer = new KafkaProducer<>(properties);
        //设置消息 不设置分区则根据hash算法选择分区 hash(key)%partitionNum
        ProducerRecord<String, String> record = new ProducerRecord<>("test", "1", "张三");
        //发送消息 同步发送消息
        RecordMetadata metadata = producer.send(record).get();
        log.info("发送到主题为:{},发送的分区为{},偏移量为{}", metadata.topic(), metadata.partition(), metadata.offset());
    }

    /**
     * @Author lipu
     * @Date 2021/11/11 19:28
     * @Description 消费者消费消息快速入门
     */
    @Test
    void acceptMsg() {
        //设置配置信息
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.206.137:9092,192.168.206.137:9093");
        //KEY-VALUE序列化
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        //设置此消费者所属的组
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        //创建消费者
        Consumer<String, String> consumer = new KafkaConsumer<>(properties);
        //订阅主题
        consumer.subscribe(Collections.singletonList("test"));

        while (true){
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, String> item : records) {
                log.info("收到到主题为:{},分区为{},偏移量为{},内容为{}的消息", item.topic(), item.partition(), item.offset(),item.value());
            }
        }
    }

    /**
     * @Author lipu
     * @Date 2021/11/15 13:02
     * @Description 消费者手动提交 以及一些细节
     */
    @Test
    void notAutoCommit() {
        /**
         * 消费者主动从topic中poll消息,每次poll消息后都会自动提交offset到_consumer_offsets,如果消费者宕机没有消费消息然而提交了offset则
         * 会导致消息丢失。
         */
        //设置配置信息
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.206.137:9092,192.168.206.137:9093");
        //KEY-VALUE序列化
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        //设置消费者的注册机制 1秒心跳; 10秒没有心跳则踢出消费组;两次poll时间间隔超过30秒踢出消费组(消费时间太长,消费能力太弱)
        properties.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG,1000);
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG,10);
        properties.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG,30*1000);
        //设置消息消费手动提交offset
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,"false");
        //设置消息拉取机制 一次性拉取500条;
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,500);
        //设置此消费者所属的组
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        //创建消费者
        Consumer<String, String> consumer = new KafkaConsumer<>(properties);
        //订阅主题
        consumer.subscribe(Collections.singletonList("test"));

        while (true){
            //设置了手动提交,在拉取消息时候进行消息消费,消费结束后需要手动提交
            //每间隔1秒进行消息拉取直到时间结束或者消息满500条就结束此次长轮询,然后执行后续逻辑,所以外套循环的作用是让kafka无线长轮询
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, String> item : records) {
                log.info("收到到主题为:{},分区为{},偏移量为{},内容为{}的消息", item.topic(), item.partition(), item.offset(),item.value());
            }
            //消息已经消费完,手动提交一下,判断拉取的消息是否有,没有可以不用提交
            if (records.count()>0){
                //提交offset 此时会被阻塞,直到kafka响应提交成功
                consumer.commitSync();
            }
        }
    }

    /**
     * @Author lipu
     * @Date 2021/11/15 14:13
     * @Description 消费者指定消费(时间、分区、offset等等)
     */
    @Test
    void assign() {
        /**
         * 知识补充:当消费者是来自一个新的消费者组或者指定的offset不存在的时候消费者是如何消费的?
         * latest(默认):只消费自己启动之后发送到主题的消息。
         * earliest:第一次从头开始消费,以后按照offset记录进行消费。
         */
        //设置配置信息
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.206.137:9092,192.168.206.137:9093");
        //KEY-VALUE序列化
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        //设置新消费组消费方式(或者offset不存在时)
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        //设置消息消费手动提交offset
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,"false");
        //设置此消费者所属的组
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        //创建消费者
        Consumer<String, String> consumer = new KafkaConsumer<>(properties);
        //指定主题以及分区
        consumer.assign(Collections.singletonList(new TopicPartition("test", 1)));
        //回溯消费--从头开始
//        consumer.seekToBeginning(Collections.singletonList(new TopicPartition("test", 1)));
        //从指定offset(偏移量)进行消费 包含当前offset 此例包含5这个offset的数据
        consumer.seek(new TopicPartition("test", 1),5);
        //指定时间点进行消费
        while (true){
            //设置了手动提交,在拉取消息时候进行消息消费,消费结束后需要手动提交
            //每间隔1秒进行消息拉取直到时间结束或者消息满500条就结束此次长轮询,然后执行后续逻辑,所以外套循环的作用是让kafka无限长轮询
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, String> item : records) {
                log.info("收到到主题为:{},分区为{},偏移量为{},内容为{}的消息", item.topic(), item.partition(), item.offset(),item.value());
            }
            //消息已经消费完,手动提交一下,判断拉取的消息是否有,没有可以不用提交
            if (records.count()>0){
                //提交offset 此时会被阻塞,直到kafka响应提交成功
                consumer.commitSync();
            }
        }
    }
}
