package com.xinzhou.dto;

import lombok.Data;

import java.io.Serializable;


@Data
public class LoginFormDTO implements Serializable {

    private Integer id;
    private String phone;
    private String password;


}
