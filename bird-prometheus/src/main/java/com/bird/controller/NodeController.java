package com.bird.controller;

import com.bird.component.PromComponent;
import com.bird.component.PromUtils;
import com.bird.entity.ChartDataResult;
import com.bird.entity.PromResult;
import com.bird.entity.bo.PromBO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author lipu
 * @Date 2021/11/2 14:27
 * @Description
 */
@RestController
public class NodeController {
    @Resource
    private PromComponent promComponent;

    @PostMapping("/get")
    public Map<String, List<ChartDataResult>> get(@RequestBody PromBO promBo) {
        PromResult promResult = promComponent.calculateCpuUsage(promBo);
        return PromUtils.dealMatrixResult(promResult);
    }

}
