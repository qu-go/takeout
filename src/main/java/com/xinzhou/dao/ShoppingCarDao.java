package com.xinzhou.dao;

import com.xinzhou.entity.Goods;
import com.xinzhou.entity.ShoppingCar;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ShoppingCarDao {
    @Select("select distinct(a.goods_store_id) from tb_goods as a inner join tb_shop_car as b on a.id=b.good_id where b.user_id=#{userId};")
    List<Integer>  getStoresListById(@Param("userId") int userId);
    @Select("select * from tb_goods as a inner join tb_shop_car as b on a.id=b.good_id where b.user_id = #{userId} and a.goods_store_id=#{store_id};")
    List<Goods> getStoreList(@Param("userId")int userId, @Param("store_id")int store_id);


    @Delete("delete from tb_shop_car where user_id=#{userID} and good_id=#{goodsId} ;")
    int  delete(@Param("userID") Integer userID, @Param("goodsId") Integer goodsId);

    @Insert("insert into `tb_shop_car` values(null,#{userId},#{goodId},#{goodNum},#{status},now(),now())")
    int addCar(ShoppingCar car);
}
