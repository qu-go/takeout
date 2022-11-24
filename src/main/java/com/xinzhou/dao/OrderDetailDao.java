package com.xinzhou.dao;

import com.xinzhou.entity.TbOrderDetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderDetailDao {

    @Insert("insert into `tb_order_detail` values(null,#{order_id},#{good_id},#{good_price},#{good_number})")
    int addOrderDetail(TbOrderDetail detail);
}
