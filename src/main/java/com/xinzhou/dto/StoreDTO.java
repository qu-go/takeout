package com.xinzhou.dto;

import com.xinzhou.entity.Goods;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class StoreDTO implements Serializable {
    private Integer id;
    public String store_name;
    public String store_icon;
    public List<Goods> goodsList;
}
