package com.xinzhou.dao;

import com.xinzhou.entity.Goods;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GoodsDao {
    List<Goods> getGoodsByQuery(@Param("currentNum") int currentNum, @Param("pageSize") int pageSize);
    List<Goods> getGoodsByTypeId(@Param("id") int id,@Param("currentNum") int currentNum, @Param("pageSize") int pageSize);

    List<Goods> getGoodsListByTwoId(@Param("type") Integer type, @Param("store_id") Integer store_id);
    @Select("select * from tb_goods where id=#{id};")
    Goods selectOne(@Param("id") Integer id);

    @Update("update `tb_goods` set  `status`= 1 where id=#{goodId}")
    Integer deleteOneById(Integer goodId);

    @Update("update `tb_goods` set goods_sell_number = goods_sell_number + 1 where id=#{goodId}")
    Integer updateSell(Integer goodId);
    @Insert("insert into `tb_goods` values(null,#{goods_name},#{goods_price}," +
            " #{goods_store_id},#{goods_type},#{goods_icon},0,5,0,now(),now())")
    Integer addOne(Goods goods);

    Integer updateGood(Goods goods);

    @Select("select * from `tb_goods` where `status` =0 and `goods_name` like concat('%',#{keyWord},'%') ;")
    List<Goods> selectByKeyWord(String keyWord);

    @Select("select count(id) from `tb_goods` where id = #{id} and `status` =0")
    int getStoreFoodCount(Integer id);
    @Select("select count(id) from `tb_goods` where `status` =0")
    Integer getAllCount();

    @Select("select * from `tb_goods` where `status` =0 limit #{offset},#{limit}")
    List<Goods> getList(@Param("offset") Integer offset,@Param("limit") Integer limit);

    @Select("select distinct(b.type) from `tb_goods` as a,`tb_goods_type` as b where a.id = #{id} and a.goods_type = b.id ")
    List<String> typeList(Integer id);
}
