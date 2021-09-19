package com.bird.reactive;

import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;

/**
 * @Author lipu
 * @Date 2021/9/14 11:24
 * @Description Subscription 接口定义了连接发布者和订阅者的方法
 * Publisher<T> 接口定义了发布者的方法
 * Subscriber<T> 接口定义了订阅者的方法
 * Processor<T,R> 接口定义了处理器
 */
public class Client {
    public static void main(String[] args) throws Exception {
        //创建发布者
        SubmissionPublisher<Object> publisher = new SubmissionPublisher<>();
        //创建订阅者
        BirdSubscriber subscriber = new BirdSubscriber();
        //设置订阅关系 此时会调用订阅者的onSubscribe
        publisher.subscribe(subscriber);
        for (int i = 0; i < 10; i++) {
            //发布消息 此时会调用订阅者的onNext
            publisher.submit("杭州台风,大家注意安全"+i);
        }
        //完成发送 此时会调用订阅者onComplete
        publisher.close();
        //主线程睡眠 模拟守护进程效果
        TimeUnit.SECONDS.sleep(600000);

    }
}
