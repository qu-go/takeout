package com.xinzhou.service;

import com.xinzhou.dto.Result;
import org.apache.ibatis.annotations.Param;

public interface CollectionService {


    Result getListService(Integer pageNum, Integer pageSize);

    Result addService(Integer good_id);

    Result deleteService(Integer good_id);
}
