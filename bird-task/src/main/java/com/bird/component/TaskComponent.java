package com.bird.component;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author lipu
 * @Date 2021/9/10 21:07
 * @Description 定时任务组件
 */
@Component
public class TaskComponent {

    public ConcurrentHashMap<String,Object> pool=new ConcurrentHashMap<>();


    /**
     * @Author lipu
     * @Date 2021/9/10 21:53
     * @Description 添加定时任务
     */
    public void add(String key){

    }

    /**
     * @Author lipu
     * @Date 2021/9/10 21:54
     * @Description 删除定时任务
     */
    public void delete(String key){

    }


}
