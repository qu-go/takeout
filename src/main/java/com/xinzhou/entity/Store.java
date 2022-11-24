package com.xinzhou.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Store implements Serializable {
    public Integer id;
    public String store_name;
    public String store_type;
    public String store_phone;
    public String store_password;
    public String store_principal;
    public String store_location;
    public String store_icon;
    public LocalDateTime store_create_time;
    public LocalDateTime store_update_time;
    public List<GoodsType> goodsTypes;

}
