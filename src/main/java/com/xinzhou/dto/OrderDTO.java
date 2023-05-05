package com.xinzhou.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xinzhou.entity.Goods;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
public class OrderDTO implements Serializable {
    private int order_id;
    private String  order_no;
    private int buyerId;
    private String receive_name;
    private String receive_phone;
    private String receive_address;
    private List<Goods> goods;
    private double total;
    private int status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime create_time;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime update_time;

}
