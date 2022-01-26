package com.bird.entity.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Author lipu
 * @Date 2021/11/2 19:30
 * @Description
 */
@Data
public class PromBO {
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+16")
    private Date start;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+16")
    private Date end;
    private String step;
    private String query;
}
