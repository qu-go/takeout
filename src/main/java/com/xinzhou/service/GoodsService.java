package com.xinzhou.service;

import com.xinzhou.dto.Result;
import com.xinzhou.entity.QueryPageBean;

public interface GoodsService {


    Result getGoodsByQueryService(QueryPageBean queryPageBean);

    Result getGoodsByTypeIdService(QueryPageBean queryPageBean);

    Result getGoodsByTypeIDAndScoreId(Integer type, Integer store_id);
}
