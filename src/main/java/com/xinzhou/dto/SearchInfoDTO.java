package com.xinzhou.dto;

import com.xinzhou.entity.Goods;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SearchInfoDTO implements Serializable {
    public List<Goods> goods;
    public List<StoreDTO> store;
    public Integer total;
}
