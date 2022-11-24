package com.xinzhou.dao;

import com.xinzhou.entity.Collections;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CollectionDao {
    //添加收藏信息
    @Insert("insert into `tb_collection` (`collection_id`,`user_id`,good_id) values(null,#{userId},#{goodId});")
    int addByGoodsId(@Param("goodId") int goodId,@Param("userId") int userId);

    @Select("select * from `tb_collection` where user_id=#{id} limit #{pageNum},#{pageSize}")
    List<Collections> selectByQuery(@Param("id")Integer id,@Param("pageNum") Integer pageNum, @Param("pageSize")Integer pageSize);

    @Select("select count(*) from `tb_collection` where user_id=#{id} and good_id= #{good_id}")
    int selectIsHaveCollection(@Param("id") Integer id , @Param("good_id") Integer good_id);
    @Delete("delete from `tb_collection` where user_id = #{id} and good_id =#{good_id}")
    int deleteOne(@Param("id") Integer id , @Param("good_id") Integer good_id);
}
