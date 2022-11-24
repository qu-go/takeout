package com.xinzhou.service.impl;

import com.xinzhou.dao.GoodsDao;
import com.xinzhou.dao.StoreDao;
import com.xinzhou.dto.Result;
import com.xinzhou.entity.Goods;
import com.xinzhou.entity.GoodsType;
import com.xinzhou.entity.Store;
import com.xinzhou.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class StoreServiceImpl implements StoreService {
    @Autowired
    private StoreDao storeDao;
    @Autowired
    private GoodsDao goodsDao;
    @Override
    public Result getStoreService(Integer store_id) {
        Store store = getStoreByIdService(store_id);
        if (store==null){
            return Result.fail("查询错误");
        }
        return Result.ok(store);
    }



    private Store getStoreByIdService(Integer id){
        Store store = storeDao.selectOneById(id);
        List<GoodsType> goodsTypes= storeDao.selectStoreTypeList(id);
        if (store!=null&&goodsTypes!=null){
            for (GoodsType goodsType : goodsTypes) {
                //根据store——id和type——id查找不同类型的商品
                List<Goods> goodListByType = goodsDao.getGoodsListByTwoId(goodsType.getId(), id);
                goodsType.setGoodsList(goodListByType);
            }
            store.setGoodsTypes(goodsTypes);
        }


        return store;
    }
}
