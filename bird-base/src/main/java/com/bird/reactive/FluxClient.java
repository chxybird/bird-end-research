package com.bird.reactive;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;


/**
 * @Author lipu
 * @Date 2021/9/14 16:01
 * @Description
 * Flux是一个发出0-N个元素组成的异步序列的Publisher 结合了JDK8的stream与JDK9的reactive stream特性
 */
@Slf4j
public class FluxClient {
    public static void main(String[] args) {
        //创建订阅者
//        BirdSubscriber subscriber = new BirdSubscriber();
        //模拟数据
        List<String> list = Arrays.asList("张三", "李四", "王五");
        //创建发布者 匿名内部类
        Subscriber<Object> subscriber = new Subscriber<>() {

            private Subscription subscription;

            @Override
            public void onSubscribe(Subscription subscription) {
                this.subscription=subscription;
                log.info("收到订阅");
                //请求一个消息
                subscription.request(1);
            }

            @Override
            public void onNext(Object o) {
                log.info("收到消息:{}",o);
                //接收到消息后再请求一个消息
                subscription.request(1);
            }

            @Override
            public void onError(Throwable throwable) {
                log.info("发生异常,终止操作");
            }

            @Override
            public void onComplete() {
                log.info("完成订阅");
            }
        };
        //创建发布者
        Flux.fromIterable(list).subscribe(subscriber);
    }
}
