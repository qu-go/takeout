package com.xinzhou.utils;

import com.xinzhou.dto.UserDTO;

public class UserHolder {
    public static final  ThreadLocal<UserDTO> tl=new ThreadLocal<>();
    public static void save(UserDTO userDTO){
        tl.set(userDTO);
    }
    public static void remove(){
        tl.remove();
    }
    public static UserDTO get(){
        return tl.get();
    }
}
