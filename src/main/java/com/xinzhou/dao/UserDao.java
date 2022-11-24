package com.xinzhou.dao;

import com.xinzhou.dto.LoginFormDTO;
import com.xinzhou.dto.UserDTO;
import com.xinzhou.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserDao {

    User selectOne(LoginFormDTO loginFormDTO);

    Integer selectByPhone(String Phone);

    int save(User user);

    int saveIconByUserDTO(UserDTO userDTO);

    @Select("select * from tb_user where id=#{id};")
    User selectById(@Param("id") Integer id);

    @Select("select id from `tb_user` where id = #{id} and password = md5(#{password});")
    int selectByIdAndPassword(@Param("id") Integer id,@Param("password") String password);

    @Update("update `tb_user` set `password` = md5( #{password}) where `id` = #{id};")
    int modifyPw(@Param("id") Integer id,@Param("password") String newPassword);
}
