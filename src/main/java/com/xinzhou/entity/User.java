package com.xinzhou.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class User implements Serializable {

    private Integer id;
    private String nick_name;
    private String password;
    private String phone;
    private String sex;
    private String icon;
    private String location;
    private LocalDateTime create_time;
    private LocalDateTime update_time;




}
