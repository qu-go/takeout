package com.xinzhou.dao;

import com.xinzhou.entity.Order;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OrderDao {

    @Insert("insert into `tb_order` values(null,#{buyerId},#{sellerId},#{receive_name},#{receive_phone}," +
            "#{receive_address},#{total},#{message},#{status},now(),now());")
    @SelectKey(statement ="select last_insert_id()",keyProperty = "order_id",keyColumn = "order_id",resultType =Integer.class,before = false )
    Integer addOrder(Order order);

    @Update("update from `tb_order set `status` = #{status} where order_id = #{orderId}")
    int updateById(@Param("orderId") Integer orderId, @Param("status") Integer status);
}
