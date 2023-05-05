package com.xinzhou.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result {
    private Boolean success;
    private Integer code;
    private Integer level;
    private String errorMsg;
    private Object data;
    private Integer total;

    public static Result ok(){
        return new Result(true, null,null, null, null,null);
    }
    public static Result ok(Object data){
        return new Result(true, null,null,null, data, null);
    }
    public static Result ok(Object data,Integer total){
        return new Result(true, null,null,null, data, total);
    }
    public static Result ok(Integer code,Object data){
        return new Result(true, code,null,null, data, null);
    }
    public static Result ok(Integer code,Integer total){
        return new Result(true, code,null,null, null, total);
    }
    public static Result ok(List<?> data, Integer total){
        return new Result(true, null,null,null, data, total);
    }
    public static Result fail(String errorMsg){
        return new Result(false,null,null,errorMsg, null, null);
    }
    public static Result ok(Integer code){
        return new Result(true,code,null,null,null,null);
    }
    public static Result fail(Integer code){
        return new Result(false,code,null,null,null,null);
    }
    public static Result fail(Integer code,String errorMsg){
        return new Result(false,code,null,errorMsg,null,null);
    }
}
