package com.xinzhou.dao;

import com.xinzhou.entity.GoodsType;
import com.xinzhou.entity.Store;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StoreDao {
    List<GoodsType> selectStoreTypeList(@Param("id") Integer store_id);
    Store selectOneById(@Param("id") Integer store_id);
    String selectStoreName(@Param("id") Integer store_id);


}
