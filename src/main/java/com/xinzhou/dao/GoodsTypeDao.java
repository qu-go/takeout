package com.xinzhou.dao;

import com.xinzhou.entity.GoodsType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GoodsTypeDao {

    @Select("select `type` from `tb_goods_type` where id = #{id}")
    String getTypeById(int id);

    @Insert("insert into values(null,#{type},null,null)")
    Integer insertOne(GoodsType goodsType);
    @Select("select * from `tb_goods_type` ")
    List<GoodsType> selectAllType();
}
