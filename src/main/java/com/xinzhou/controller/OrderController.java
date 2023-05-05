package com.xinzhou.controller;

import com.xinzhou.dto.Result;
import com.xinzhou.entity.Order;
import com.xinzhou.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/commit")
    public Result commitOrder(@RequestBody Order order) throws IOException {
        return orderService.commitService(order);
    }
    @GetMapping("/update/status")
    public Result updateStatus(Integer orderId,Integer status) throws IOException {
        if (orderId <=0 || status <0){
            return Result.fail(202,"上传参数有误");
        }
        Result result;
        try{
             result=orderService.updateStatusService(orderId, status);
        }catch (RuntimeException run){
            return Result.fail(203, run.getMessage());
        }
        return result;

    }

    @GetMapping("/get/done")
    public Result getOldOrderDone(){
        return orderService.getAllOrderDone();
    }

    @GetMapping("/new")
    public Result getNew(){
        return orderService.getNewOrder();
    }
    @GetMapping("/user/all")
    public Result getUserAllOrder(){
        return orderService.getAllByUserId();
    }


    @GetMapping("/list")
    public Result getListOrder(Integer offset,Integer limit){
        return orderService.getListService(offset,limit);
    }

    @GetMapping(value = "/{date}/count")
    public Result countDayCount(@PathVariable String date){

        return orderService.orderDayCount(date);
    }

    @GetMapping("/count")
    public Result getAllOrderCount(){
        return orderService.orderAllCountService();
    }

}
