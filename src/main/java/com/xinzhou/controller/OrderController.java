package com.xinzhou.controller;

import com.xinzhou.dto.Result;
import com.xinzhou.entity.Order;
import com.xinzhou.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/commit")
    public Result commitOrder(@RequestBody Order order){

        return orderService.commitService(order);
    }
    @GetMapping("/update/status")
    public Result updateStatus(Integer orderId,Integer status){
        if (orderId <=0 || status <0){
            return Result.fail(202,"上传参数有误");
        }
        return orderService.updateStatusService(orderId,status);

    }
}
