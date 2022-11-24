package com.xinzhou.utils;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.xinzhou.dto.Result;
import com.xinzhou.dto.UserDTO;
import org.springframework.util.StringUtils;

public class CommonUtil {
    //拼接存储在concurrentHashMap的key用来获取session
    public static  String splitKey(String userId,String identity){
        if (StringUtils.isEmpty(userId)||StringUtils.isEmpty(identity)){
            return "";
        }
        String key=userId+":"+identity;
        return key.trim();
    }

    public static boolean isUseDtoNull(UserDTO userDTO){
        if (userDTO==null) {
            return true;
        }
        return false;
    }
}
