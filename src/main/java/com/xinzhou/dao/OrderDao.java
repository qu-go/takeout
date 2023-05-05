package com.xinzhou.dao;

import com.xinzhou.entity.Order;
import com.xinzhou.entity.TbOrderDetail;
import org.apache.ibatis.annotations.*;
import org.junit.runners.Parameterized;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderDao {

    @Insert("insert into `tb_order` values(null,#{order_no},#{buyerId},#{sellerId},#{receive_name},#{receive_phone}," +
            "#{receive_address},#{total},#{message},#{status},now(),now());")
    @SelectKey(statement ="select last_insert_id()",keyProperty = "order_id",keyColumn = "order_id",resultType =Integer.class,before = false )
    Integer addOrder(Order order);

    @Update("update  `tb_order` set `status` = #{status} where order_id = #{orderId}")
    int updateById(@Param("orderId") Integer orderId, @Param("status") Integer status);

    @Select("select * from `tb_order` where sellerId = #{sellerId} ")
    List<Order> selectAllBySellerId(Integer  sellerId);

    @Select("select count(order_id) from `tb_order` where sellerId = #{sellerId} and create_time >= #{time}")
    Integer selectOrderBySellerId(@Param("sellerId") Integer  sellerId,@Param("time") LocalDateTime time);

    @Select("select * from `tb_order` where buyerId = #{buyerId} ")
    List<Order> selectAllByBuyerId(Integer  buyerId);

    //返回食品的编号
    @Select("select * from `tb_order_detail` where  `order_id`= #{orderId} ")
    List<TbOrderDetail> selectOrderTail(@Param("orderId") Integer orderId);

    @Select("select `buyerId` from `tb_order` where `order_id` = #{orderId}")
    Integer selectBuyerId(Integer orderId);
    @Select(("select count(order_id)  from `tb_order` where `create_time` >= #{start} and `create_time` <= #{end}"))
    int getOrderNew(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Select("select count(order_id) from `tb_order`")
    Integer getOrderCount();

    @Select("select * from `tb_order` order by update_time DESC limit #{offset},#{limit} ")
    List<Order> getList(@Param("offset") Integer offset, @Param("limit") Integer limit);
}
