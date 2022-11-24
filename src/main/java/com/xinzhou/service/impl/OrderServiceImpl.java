package com.xinzhou.service.impl;

import com.xinzhou.dao.OrderDao;
import com.xinzhou.dao.OrderDetailDao;
import com.xinzhou.dto.Result;
import com.xinzhou.entity.Order;
import com.xinzhou.entity.TbOrderDetail;
import com.xinzhou.service.OrderService;
import com.xinzhou.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Consumer;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao dao;
    @Autowired
    private OrderDetailDao orderDetailDao;
    @Override
    public Result commitService(Order order) {
        if (order==null ){
            return Result.fail(203,"订单上传信息错误");
        }
        order.setBuyerId(UserHolder.get().getId());
        //生成订单
        //向订单表添加数据
        Integer orderId = dao.addOrder(order);
        //向订单详细表，添加数据
        if (orderId<=0){
            return Result.fail(202,"添加订单失败");
        }
        order.setOrder_id(order.getOrder_id());
        List<TbOrderDetail> orderDetails = order.getOrderDetails();

        orderDetails.forEach(detail -> {
            detail.setOrder_id(order.getOrder_id());
            orderDetailDao.addOrderDetail(detail);
        });
        //返回订单
        return Result.ok(order);
    }

    @Override
    public Result updateStatusService(Integer orderId, Integer status) {

        if (dao.updateById(orderId,status)<=0){
            Result.fail(402,"修改订单失败");
        }
        return Result.ok();
    }
}
