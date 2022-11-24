package com.xinzhou.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.xinzhou.dto.StoreDTO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
public class ShoppingCar implements Serializable {
        private Integer carId;
        private Integer userId;
        private Integer goodId;
        private Integer goodNum;
        private Integer status;
        private LocalDateTime createTime;
        private LocalDateTime updateTime;
        List<StoreDTO> storeDTOS;
}
