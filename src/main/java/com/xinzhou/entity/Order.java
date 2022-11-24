package com.xinzhou.entity;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data

public class Order {

  private Integer order_id;
  private Integer buyerId;
  private Integer sellerId;
  private String receive_name;
  private String receive_phone;
  private String receive_address;
  private double total;
  private String message;
  private Integer status;
  private List<TbOrderDetail> orderDetails;
  private LocalDateTime createTime;
  private LocalDateTime updateTime;




}
