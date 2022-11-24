package com.xinzhou.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentResponse implements Serializable {
    public Integer id;
    public String store_name;
    public List<Comment> commentList;
}
