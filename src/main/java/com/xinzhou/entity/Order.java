package com.xinzhou.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
public class Order {

  private Integer order_id;
  private String order_no;
  private Integer buyerId;
  private Integer sellerId;
  private String receive_name;
  private String receive_phone;
  private String receive_address;
  private double total;
  private String message;
  private Integer status;
  private List<TbOrderDetail> orderDetails;
  private List<Goods> goods;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private LocalDateTime create_time;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private LocalDateTime update_time;





}
