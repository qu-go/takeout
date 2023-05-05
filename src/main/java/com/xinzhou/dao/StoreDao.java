package com.xinzhou.dao;

import com.xinzhou.dto.UserDTO;
import com.xinzhou.entity.GoodsType;
import com.xinzhou.entity.Store;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StoreDao {
    List<GoodsType> selectStoreTypeList(@Param("id") Integer store_id);
    Store selectOneById(@Param("id") Integer store_id);
    String selectStoreName(@Param("id") Integer store_id);

    @Select("select count(id) from  `tb_store` where `store_phone` = #{phone}")
    int selectByPhone(String phone);

    @Select("select * from  `tb_store` where `store_phone` = #{phone}")
    Store selectOneByPhone(String phone);
    @Insert("insert into `tb_store` values(null,#{store_name},#{store_phone},#{store_type},md5(#{store_password}),#{store_principal},#{store_location},#{store_icon},now(),now())")
    int addOne(Store store);

    @Select("select id from `tb_store` where id = #{id} and store_password = md5(#{password});")
    int selectByIdAndPassword(@Param("id") Integer id,@Param("password") String password);

    @Update("update `tb_store` set `store_password` = md5( #{password}) where `id` = #{id};")
    int modifyPw(@Param("id") Integer id,@Param("password") String newPassword);

    @Update("update `tb_store` set `store_icon` = #{icon}  where `id` = #{id};")
    int saveIconByUserDTO(UserDTO userDTO);

    @Select("select * from `tb_store` where `store_name` like  concat('%',#{keyWord},'%');")
    List<Store> selectNameByKeyWord(String keyWord);

    @Select("select count(id) from `tb_store` ")
    int getAllCount();
    @Select("select * from `tb_store` limit #{offset},#{limit}")
    List<Store> getList(@Param("offset") Integer offset,@Param("limit") Integer limit);

    Integer updateStore(Store store);

    @Delete("delete from `tb_store` where `id` = #{id}")
    Integer deleteById(Integer id);
}
