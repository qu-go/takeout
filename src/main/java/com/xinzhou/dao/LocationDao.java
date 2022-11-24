package com.xinzhou.dao;

import com.xinzhou.entity.Location;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LocationDao {

    @Select("select * from `tb_user_location` where user_id=#{id}")
    List<Location> selectAllBy(Integer id);

    @Update("update `tb_user_location` set `receive_name` = #{location.receive_name}," +
            " `receive_phone` = #{location.receive_phone},`receive_location`= #{location.receive_location}," +
            "`status` = #{location.status} where `user_id` = #{id} and `id` = #{location.id};")
    int updateByLocation(@Param("id") Integer id, @Param("location") Location location);
    @Update("update `tb_user_location` set `status` = 0 where user_id = #{id};")
    int updateStatusDefault(@Param("id") Integer id);

    @Insert("insert into `tb_user_location` values(null,#{location.user_id},#{location.receive_name}" +
            ",#{location.receive_phone},#{location.receive_location},#{location.status},now(),now())")
    int insert(@Param("location") Location location);
}
