package com.xinzhou.dto;

import com.xinzhou.entity.Store;
import lombok.Data;

import java.io.Serializable;

@Data
public class StoreInfoDTO implements Serializable {
    public StoreDTO storedto;
    public Integer daySale;
    public Integer weekSale;
    public Integer monthSale;
}
