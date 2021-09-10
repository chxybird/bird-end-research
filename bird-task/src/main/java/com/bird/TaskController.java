package com.bird;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lipu
 * @Date 2021/9/10 17:04
 * @Description
 */
@RestController
@RequestMapping("/task")
public class TaskController {

    /**
     * @Author lipu
     * @Date 2021/9/10 17:05
     * @Description 创建任务
     */
    @PostMapping("create-task")
    public void createTask(){

    }

    /**
     * @Author lipu
     * @Date 2021/9/10 17:05
     * @Description 开启任务
     */
    @PostMapping("start-task")
    public void startTask(){

    }

    /**
     * @Author lipu
     * @Date 2021/9/10 17:06
     * @Description 停止任务
     */
    @PostMapping("stop-task")
    public void stopTask(){

    }
}
