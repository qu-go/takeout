package com.xinzhou.entity;


import lombok.Data;

@Data
public class TbOrderDetail {

  private Integer order_detail_id;
  private Integer order_id;
  private Integer good_id;
  private double good_price;
  private Integer good_number;




}
