package com.xinzhou.entity;

import com.alibaba.druid.support.monitor.annotation.MTable;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Goods implements Serializable {
    public Integer id;
    public String goods_name;
    public double goods_price;
    public int goods_store_id;
    public String store_location;
    public String store_name;
    public int goods_type;
    public String type;
    public String goods_icon;
    public int goods_sell_number;
    public int goods_score;
    public int good_num;
    public int status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public LocalDateTime goods_create_time;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public LocalDateTime goods_update_time;

}
