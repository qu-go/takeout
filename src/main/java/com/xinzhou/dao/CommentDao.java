package com.xinzhou.dao;

import com.xinzhou.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentDao {


    //通过条件查询评论
    @Select("select * from tb_goods_comment where store_id=#{storeId} limit #{page},#{size}")
    List<Comment> selectByQuery(@Param("storeId") Integer storeId, @Param("page") Integer page,@Param("size") Integer pageSize);
}
