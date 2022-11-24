package com.xinzhou.service.impl;

import com.xinzhou.dao.CollectionDao;
import com.xinzhou.dao.GoodsDao;
import com.xinzhou.dto.Result;
import com.xinzhou.dto.UserDTO;
import com.xinzhou.entity.Collections;
import com.xinzhou.entity.Goods;
import com.xinzhou.service.CollectionService;
import com.xinzhou.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class CollectionServiceImpl  implements CollectionService {

    @Autowired
    private CollectionDao dao;
    @Autowired
    private GoodsDao goodsDao;


    @Override
    public Result getListService(Integer pageNum, Integer pageSize) {
        UserDTO userDTO = UserHolder.get();
        if (userDTO==null){
            return Result.fail(201,"没有用户信息重新登陆");
        }
        List<Collections> collections=dao.selectByQuery(userDTO.getId(), pageNum*pageSize,pageSize);
        List<Collections> collect = collections.stream()
                .map(collections1 -> collections1.setGoods(goodsDao.selectOne(collections1.getGood_id())))
                .collect(Collectors.toList());
        return Result.ok(collect,collect.size());
    }

    @Override
    public Result addService(Integer good_id) {
        UserDTO userDTO = UserHolder.get();
        //通过
        //通过goods_id查找商品
        if (dao.selectIsHaveCollection(userDTO.getId(), good_id)>0) {
            return Result.ok();
        }
        int i = dao.addByGoodsId(good_id, userDTO.getId());
        return i>0 ? Result.ok() : Result.fail(201,"添加失败");
    }

    public Result deleteService(Integer good_id){
        UserDTO userDTO = UserHolder.get();
        if (dao.selectIsHaveCollection(userDTO.getId(), good_id)<=0) {
            return Result.ok();
        }
        int i = dao.deleteOne(userDTO.getId(), good_id);
        return i>0 ? Result.ok() : Result.fail(201,"删除失败");
    }


}
