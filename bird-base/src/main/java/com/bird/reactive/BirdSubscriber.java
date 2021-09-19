package com.bird.reactive;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Flow;

/**
 * @Author lipu
 * @Date 2021/9/14 13:47
 * @Description 订阅者 采用reactive stream(响应流编程规范) JDK9的实现为Flow API
 */
@Slf4j
public class BirdSubscriber implements Flow.Subscriber<Object> {

    private Flow.Subscription subscription;
    /**
     * @Author lipu
     * @Date 2021/9/14 13:49
     * @Description 发布者调用订阅者的这个方法来异步传递订阅,这个方法在 publisher.subscribe方法调用后被执行
     */
    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription=subscription;
        log.info("收到订阅");
        //请求一个消息
        subscription.request(1);
    }



    /**
     * @Author lipu
     * @Date 2021/9/14 13:50
     * @Description 发布者调用这个方法传递数据给订阅者
     */
    @Override
    public void onNext(Object item) {
        log.info("收到消息:{}",item);
        //接收到消息后再请求一个消息
        subscription.request(1);
    }

    /**
     * @Author lipu
     * @Date 2021/9/14 13:50
     * @Description 当Publisher或Subscriber遇到不可恢复的错误时调用此方法,之后不会再调用其他方法
     */
    @Override
    public void onError(Throwable throwable) {
        log.info("发生异常,终止操作");
    }

    /**
     * @Author lipu
     * @Date 2021/9/14 13:50
     * @Description 当数据已经发送完成,且没有错误导致订阅终止时,调用此方法,之后不再调用其他方法
     */
    @Override
    public void onComplete() {
        log.info("完成订阅");
    }
}
