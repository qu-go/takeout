package com.xinzhou.service.impl;

import com.xinzhou.dao.GoodsDao;
import com.xinzhou.dto.Result;
import com.xinzhou.entity.Goods;
import com.xinzhou.entity.QueryPageBean;
import com.xinzhou.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    public GoodsDao dao;
    @Override
    public Result getGoodsByQueryService(QueryPageBean queryPageBean) {
        if (queryPageBean==null){
            return Result.fail("信息上传错误");
        }

        List<Goods> goodsList = dao.getGoodsByQuery(queryPageBean.getCurrentPage() * 10, queryPageBean.getPageSize());
        if (goodsList==null){
            return Result.fail("没有信息");
        }

        return Result.ok(goodsList,  goodsList.size());
    }

    @Override
    public Result getGoodsByTypeIdService(QueryPageBean queryPageBean) {
        if (queryPageBean==null){
            return Result.fail("信息上传错误");
        }
        List<Goods> goodsByTypeId = dao.getGoodsByTypeId(Integer.valueOf(queryPageBean.getQuery()), queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        if (goodsByTypeId!=null&& goodsByTypeId.size()>0){
            return Result.ok(goodsByTypeId,goodsByTypeId.size());
        }
        return Result.fail("没有信息");
    }

    @Override
    public Result getGoodsByTypeIDAndScoreId(Integer type, Integer store_id) {
        List<Goods> goods=dao.getGoodsListByTwoId(type,store_id);
        if (goods==null){
            return Result.fail("没有该数据");
        }
        return Result.ok(goods,goods.size());
    }
}
