package com.bird.component;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * @Author lipu
 * @Date 2021/9/10 21:07
 * @Description 定时任务组件
 */
@Component
public class TaskComponent {

    private final ConcurrentHashMap<String, ScheduledFuture<?>> map = new ConcurrentHashMap<>();

    @Resource
    private ThreadPoolTaskScheduler scheduler;


    /**
     * @Author lipu
     * @Date 2021/9/10 21:53
     * @Description 添加定时任务并启动
     */
    public void start(String key,String cron) {
        //创建任务,并加入到map容器
        ScheduledFuture<?> future = scheduler.schedule(() -> {
            //执行的业务逻辑
            System.out.println(Thread.currentThread().getName()+":逻辑执行完毕!!!");
        }, new CronTrigger(cron));
        map.put(key,future);
    }


    /**
     * @Author lipu
     * @Date 2021/9/11 14:19
     * @Description 停止定时任务并删除
     */
    public void stop(String key){
        ScheduledFuture<?> future = map.get(key);
        if (future!=null){
            future.cancel(true);
        }
    }

}
