package com.xinzhou.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.xinzhou.entity.Goods;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
public class StoreDTO implements Serializable {
    private Integer id;
    public String store_name;
    public String store_icon;
    public String store_type;
    public String store_phone;
    public  Integer sales;
    public String store_principal;
    public String store_location;
    public List<Goods> goodsList;
}
