package com.bird.controller;

import com.bird.component.TaskComponent;
import com.bird.domain.TaskQo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author lipu
 * @Date 2021/9/10 17:04
 * @Description
 */
@RestController
@RequestMapping("/task")
public class TaskController {
    @Resource
    private TaskComponent taskComponent;

    /**
     * @Author lipu
     * @Date 2021/9/10 17:05
     * @Description 创建任务
     */
    @PostMapping("create-task")
    public void createTask(@RequestBody TaskQo taskQo){
        taskComponent.start(taskQo.getTaskName(),taskQo.getCron());
    }

    /**
     * @Author lipu
     * @Date 2021/9/10 17:06
     * @Description 停止任务
     */
    @PostMapping("stop-task")
    public void stopTask(String taskName){
        taskComponent.stop(taskName);
    }
}
