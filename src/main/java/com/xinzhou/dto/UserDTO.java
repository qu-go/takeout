package com.xinzhou.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class UserDTO implements Serializable {
    private Integer id;
    private String nick_name;
    private String icon;

}
