package com.xinzhou.dao;

import com.xinzhou.entity.SystemUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface SystemUserDao {


    @Select("select count(`id`) from `tb_system_user`")
    Integer selectCount();

    @Select("select * from `tb_system_user`")
    List<SystemUser> getList();

    @Select("select count(`id`) from `tb_system_user` where `create_time` >= #{start} and `create_time` <= #{end}")
    Integer getNewAdd(@Param("start") LocalDateTime startDay, @Param("end") LocalDateTime nextDay);
}
