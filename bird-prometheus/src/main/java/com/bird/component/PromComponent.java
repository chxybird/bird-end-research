package com.bird.component;

import com.bird.entity.PromResult;
import com.bird.entity.bo.PromBO;
import com.bird.entity.prom.PromMatrix;
import com.bird.utils.JsonUtils;
import com.bird.utils.RestUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author lipu
 * @Date 2021/11/2 13:10
 * @Description
 */
@Component
public class PromComponent {

    @Resource
    private RestTemplate restTemplate;

    /**
     * @Author lipu
     * @Date 2021/11/2 13:10
     * @Description 计算CPU的使用率
     */
    public PromResult calculateCpuUsage(PromBO promBo) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Map<String, Object> param = new HashMap<>(8);
        param.put("query", "100 - (avg by(instance) (irate(node_cpu_seconds_total{mode=\"idle\"}[5m])) * 100)");
        param.put("start", format.format(promBo.getStart()));
        param.put("end", format.format(promBo.getEnd()));
        param.put("step", promBo.getStep());
        String json = restTemplate.getForObject("http://192.168.206.137:9090/api/v1/query_range?query={query}&start={start}&end={end}&step={step}", String.class, param);
        return dealResult(json);
    }

    /**
     * @Author lipu
     * @Date 2021/11/2 16:08
     * @Description 结果集处理
     */
    public PromResult dealResult(String sourceData) {
        PromResult promResult = JsonUtils.jsonToObject(sourceData, PromResult.class);
        //根据不同的返回值处理不同的结果集
        String resultType = promResult.getData().getResultType();
        if ("matrix".equals(resultType)) {
            Object result = promResult.getData().getResult();
            String resultJson = JsonUtils.entityToJson(result);
            List<PromMatrix> promMatrixList = JsonUtils.jsonToList(resultJson, PromMatrix.class);
            promResult.getData().setResult(promMatrixList);
        }
        return promResult;
    }
}
