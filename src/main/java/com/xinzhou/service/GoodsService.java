package com.xinzhou.service;

import com.xinzhou.dto.Result;
import com.xinzhou.entity.Goods;
import com.xinzhou.entity.GoodsType;
import com.xinzhou.entity.QueryPageBean;

public interface GoodsService {


    Result getGoodsByQueryService(QueryPageBean queryPageBean);

    Result getGoodsByTypeIdService(QueryPageBean queryPageBean);

    Result getGoodsByTypeIDAndScoreId(Integer type, Integer store_id);

    Result downGoodService(Integer goodId);

    Result getGoodById(Integer goodId);

    Result updateService(Goods good);

    Result addService(Goods good);

    Result searchService(String text);

    Result getFoodCountService(Integer id);

    Result getGoodsListService(Integer offset, Integer limit);


    Result getTypeService(Integer id);

    Result insertType(GoodsType goodsType);

    Result getAllTypeService();
}
