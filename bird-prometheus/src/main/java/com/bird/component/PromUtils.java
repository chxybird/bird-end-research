package com.bird.component;

import com.bird.entity.ChartDataResult;
import com.bird.entity.PromResult;
import com.bird.entity.prom.PromMatrix;
import com.bird.entity.prom.PromTest;
import com.bird.entity.prom.PromVector;
import com.bird.utils.DateUtils;
import com.bird.utils.JsonUtils;

import java.util.*;

/**
 * @Author lipu
 * @Date 2021/11/2 17:53
 * @Description
 */
public class PromUtils {

    /**
     * @Author lipu
     * @Date 2021/11/2 17:54
     * @Description 将数据处理成报表统计结果集
     */
    public static Map<String, List<ChartDataResult>> dealMatrixResult(PromResult promResult) {
        List<PromMatrix> result = JsonUtils.jsonToList(JsonUtils.entityToJson(promResult.getData().getResult()), PromMatrix.class);
        Map<String, List<ChartDataResult>> map = new HashMap<>();
        result.forEach(item -> {
            List<ChartDataResult> chartDataResultList = new ArrayList<>();
            List<List<Object>> valueList = item.getValues();
            valueList.forEach(value -> {
                ChartDataResult chartDataResult = new ChartDataResult();
                String key = DateUtils.timeStampToStr(((Double) value.get(0)).longValue()*1000);
                chartDataResult.setKey(key);
                chartDataResult.setValue(String.valueOf(value.get(1)));
                chartDataResultList.add(chartDataResult);
            });
            map.put(item.getMetric().get("instance"),chartDataResultList);
        });
        return map;
    }

    public static void main(String[] args) {
        String json="[\n" +
                "  {\n" +
                "    \"metric\": {\n" +
                "      \"node\": \"kube-master\"\n" +
                "    },\n" +
                "    \"value\": [\n" +
                "      1.635922480143E9,\n" +
                "      \"1\"\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"metric\": {\n" +
                "      \"node\": \"worker1-harbor\"\n" +
                "    },\n" +
                "    \"value\": [\n" +
                "      1.635922480143E9,\n" +
                "      \"1\"\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"metric\": {\n" +
                "      \"node\": \"kube-worker-2\"\n" +
                "    },\n" +
                "    \"value\": [\n" +
                "      1.635922480143E9,\n" +
                "      \"1\"\n" +
                "    ]\n" +
                "  }\n" +
                "]";
        String jsonOne="{\n" +
                "  \"value\": 1.635922480143E9\n" +
                "}";
//        List<PromVector> promVectors = JsonUtils.jsonToList(json, PromVector.class);
//        System.out.println(promVectors);
        PromTest promTest = JsonUtils.jsonToObject(jsonOne, PromTest.class);
        System.out.println(promTest);
    }
}
