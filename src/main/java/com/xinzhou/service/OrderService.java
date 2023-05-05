package com.xinzhou.service;

import com.xinzhou.dto.Result;
import com.xinzhou.entity.Order;

import java.io.IOException;

public interface OrderService {
    Result commitService(Order order) throws IOException;

    Result updateStatusService(Integer orderId, Integer status) throws IOException;
    Result getNewOrder();

    Result getAllOrderDone();

    Result getAllByUserId();

    Result orderDayCount(String date);

    Result orderAllCountService();

    Result getListService(Integer offset, Integer limit);
}
