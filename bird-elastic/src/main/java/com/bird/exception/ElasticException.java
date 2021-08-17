package com.bird.exception;

/**
 * @Author lipu
 * @Date 2021/8/17 14:30
 * @Description 自定义异常处理类
 */
public class ElasticException extends RuntimeException{

    public ElasticException() {
    }

    public ElasticException(String message) {
        super(message);
    }
}
