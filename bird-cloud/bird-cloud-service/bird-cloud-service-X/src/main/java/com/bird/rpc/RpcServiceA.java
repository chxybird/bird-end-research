package com.bird.rpc;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @Author lipu
 * @Date 2021/9/8 13:51
 * @Description
 */
@Component
public class RpcServiceA {
    @Resource
    private RestTemplate restTemplate;

    /**
     * @Author lipu
     * @Date 2021/9/8 13:52
     * @Description 请求微服务A
     */
    public String getInfo(){
        ResponseEntity<String> responseEntity = restTemplate.exchange("http://127.0.0.1:30000/cloud-a/get-info",
                HttpMethod.GET, null, String.class);
        return responseEntity.getBody();
    }

}
