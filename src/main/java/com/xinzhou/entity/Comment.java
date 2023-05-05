package com.xinzhou.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.xinzhou.dto.UserDTO;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
public class Comment implements Serializable {
    public Integer id;
    public Integer good_id;
    public Integer store_id;
    public Integer user_id;
    public String goods_name;
    public String image;
    public String text;
    public Integer score;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public LocalDateTime create_time;
    public UserDTO userDTO;
}
