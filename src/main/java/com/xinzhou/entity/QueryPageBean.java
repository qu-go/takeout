package com.xinzhou.entity;

import io.lettuce.core.ScriptOutputType;
import lombok.Data;

import java.io.Serializable;

@Data
public class QueryPageBean implements Serializable {
    public int currentPage;
    public int pageSize;
    public String query;

}
