package com.xinzhou.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
public class Location implements Serializable {
    private Integer id;
    private Integer user_id;
    private String receive_name;
    private String receive_phone;
    private String receive_location;
    private Integer status;
    private String icon;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
}
