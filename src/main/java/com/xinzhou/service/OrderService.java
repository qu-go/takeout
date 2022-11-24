package com.xinzhou.service;

import com.xinzhou.dto.Result;
import com.xinzhou.entity.Order;

public interface OrderService {
    Result commitService(Order order);

    Result updateStatusService(Integer orderId, Integer status);
}
