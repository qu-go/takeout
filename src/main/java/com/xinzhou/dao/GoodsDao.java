package com.xinzhou.dao;

import com.xinzhou.entity.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GoodsDao {
    List<Goods> getGoodsByQuery(@Param("currentNum") int currentNum, @Param("pageSize") int pageSize);
    List<Goods> getGoodsByTypeId(@Param("id") int id,@Param("currentNum") int currentNum, @Param("pageSize") int pageSize);

    List<Goods> getGoodsListByTwoId(@Param("type") Integer type, @Param("store_id") Integer store_id);
    @Select("select * from tb_goods where id=#{id};")
    Goods selectOne(@Param("id") Integer id);
}
