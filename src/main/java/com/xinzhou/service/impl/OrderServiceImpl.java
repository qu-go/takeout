package com.xinzhou.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.xinzhou.dao.GoodsDao;
import com.xinzhou.dao.OrderDao;
import com.xinzhou.dao.OrderDetailDao;
import com.xinzhou.dao.StoreDao;
import com.xinzhou.dto.OrderDTO;
import com.xinzhou.dto.Result;
import com.xinzhou.dto.UserDTO;
import com.xinzhou.entity.Goods;
import com.xinzhou.entity.Order;
import com.xinzhou.entity.TbOrderDetail;
import com.xinzhou.handler.HttpAuthHandler;
import com.xinzhou.service.OrderService;
import com.xinzhou.utils.CommonUtil;
import com.xinzhou.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao dao;
    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private StoreDao storeDao;
    @Autowired
    private GoodsDao goodsDao;
    @Override
    public Result commitService(Order order) throws IOException {
        System.out.println(order);
        if (order==null ){
            return Result.fail(203,"订单上传信息错误");
        }
        order.setBuyerId(UserHolder.get().getId());
        order.setReceive_name(UserHolder.get().getNick_name());
        //生成编号
        order.setOrder_no(CommonUtil.get16UUID());
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
        //通知店铺
        HttpAuthHandler handler=new HttpAuthHandler();
        handler.sendToUser(CommonUtil.splitKey(String.valueOf(order.getSellerId()), "2"),"你有新订单");
        return Result.ok(order);
    }

    @Override
    public Result updateStatusService(Integer orderId, Integer status) throws IOException {
        if (dao.updateById(orderId,status)<=0){
            Result.fail(402,"修改订单失败");
        }
        if(status==4){
            //通知用户，商家已经接单
            //通过订单号，查找用户的id
            Integer userId = dao.selectBuyerId(orderId);
            if(userId<=0){
                return Result.fail(202,"查找错误");
            }
            HttpAuthHandler handler=new HttpAuthHandler();
            List<TbOrderDetail> tbOrderDetails = orderDetailDao.selectByOrderId(orderId);
            //修改销售量
            tbOrderDetails.forEach(detail -> goodsDao.updateSell(detail.getGood_id()));
//            if (handler.getSession(CommonUtil.splitKey(String.valueOf(userId), "1"))==null) {
//                throw new RuntimeException("用户不在线");
//            }
            handler.sendToUser(CommonUtil.splitKey(String.valueOf(userId), "1"), "商家已接单");
        }
        return Result.ok();
    }

    @Override
    public Result getNewOrder(){
        if(UserHolder.get()==null){
            return Result.fail(202);
        }
        List<OrderDTO> res = getOrderByCondition(0);
        return res==null || res.size()<=0  ? Result.fail(408) :Result.ok(res, res.size());

    }

    @Override
    public Result getAllOrderDone() {
        UserDTO userDTO = UserHolder.get();
        if (userDTO==null){
            return Result.fail(404,"请重新登录");
        }
        List<OrderDTO> res = getOrderByCondition(6);
        //排序
        if (res!=null){
            res=res.stream().sorted(Comparator.comparingInt(OrderDTO::getOrder_id)).collect(Collectors.toList());
        }
        return res==null ||res.size()<=0  ? Result.fail(408) :Result.ok(res, res.size());
    }

    @Override
    public Result getAllByUserId() {
        UserDTO userDTO = UserHolder.get();
        if (userDTO==null){
            return Result.fail(404,"请重新登录");
        }
        List<Order> orders = dao.selectAllByBuyerId(userDTO.getId());
        if(orders==null || orders.size()<=0){
            return Result.fail(202,"没有订单");
        }
        //根据订单号查询订单购买的food
        List<Order> collect = orders.stream().distinct()
                .map(order -> order.setGoods(orderDetailDao.selectByOrderId(order.getOrder_id()).stream().distinct()
                        .map(detail -> goodsDao.selectOne(detail.getGood_id()).setGood_num(detail.getGood_number()).setStore_name(storeDao.selectStoreName(order.getSellerId()))). collect(Collectors.toList())))
                .filter(order -> order.getGoods().size()>0)
                .sorted((o1, o2) -> o2.getCreate_time().compareTo(o1.getCreate_time()))
                .collect(Collectors.toList());
        return Result.ok(collect);
    }

    @Override
    public Result orderDayCount(String date) {
        if (StringUtils.isEmpty(date)){
            return Result.fail(420,"日期不能为空");
        }
        int result = dao.getOrderNew(CommonUtil.getStartDay(date), CommonUtil.getNextDay(date));
        return Result.ok(200,result);
    }

    @Override
    public Result orderAllCountService() {
        UserDTO userDTO = UserHolder.get();
        if (userDTO==null){
            return Result.fail(404,"请重新登录");
        }
        return Result.ok(200,dao.getOrderCount());
    }

    @Override
    public Result getListService(Integer offset, Integer limit) {
        List<Order> orders = dao.getList(offset,limit);
        return Result.ok(200,orders);
    }


    /**
     * 根据状态返回订单
     * @param status
     * @return
     */

    public List<OrderDTO> getOrderByCondition(Integer status){
        List<Order> collect = dao.selectAllBySellerId(UserHolder.get().getId())
                .stream()
                .filter(order1 -> order1.getStatus().equals(status))
                .collect(Collectors.toList());
        List<OrderDTO> res = collect.stream()
                .map(order12 -> BeanUtil.copyProperties(order12, OrderDTO.class).setGoods(dao.selectOrderTail(order12.getOrder_id())
                        .stream()
                        .map(detail -> goodsDao.selectOne(detail.getGood_id()).setGood_num(detail.getGood_number()))
                        .collect(Collectors.toList())))
                .collect(Collectors.toList());
        return res.size()<=0 ? null : res;

    }
}
