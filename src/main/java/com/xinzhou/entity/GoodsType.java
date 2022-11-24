package com.xinzhou.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GoodsType implements Serializable {
    public Integer id;
    public String type;
    public List<Goods> goodsList;
}
