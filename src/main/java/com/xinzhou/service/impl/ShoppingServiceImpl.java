package com.xinzhou.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.xinzhou.dao.CollectionDao;
import com.xinzhou.dao.GoodsDao;
import com.xinzhou.dao.ShoppingCarDao;
import com.xinzhou.dao.StoreDao;
import com.xinzhou.dto.Result;
import com.xinzhou.dto.StoreDTO;
import com.xinzhou.dto.UserDTO;
import com.xinzhou.entity.Goods;
import com.xinzhou.entity.ShoppingCar;
import com.xinzhou.entity.Store;
import com.xinzhou.service.ShoppingCarService;
import com.xinzhou.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class ShoppingServiceImpl implements ShoppingCarService {

    @Autowired
    private ShoppingCarDao carDao;
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private StoreDao storeDao;
    @Autowired
    private CollectionDao collectionDao;
    @Override
    public Result getGoods() {
        List<StoreDTO>  storeDTOS=new ArrayList<>();
        ShoppingCar shoppingCar = new ShoppingCar();
        //获取当前的用户
        UserDTO userDTO = UserHolder.get();
        if (userDTO==null){
            return Result.fail(202,"请重新登录");
        }
        List<Integer> storeIds = carDao.getStoresListById( userDTO.getId());
        for (Integer storeId : storeIds) {
            Store store = storeDao.selectOneById(storeId);
            StoreDTO storeDTO = BeanUtil.toBeanIgnoreCase(store, StoreDTO.class, true);
            List<Goods> storeList = carDao.getStoreList( userDTO.getId(), storeId);
            if (storeDTO!=null && storeList!=null){
                storeDTO.setGoodsList(storeList);
            }
            storeDTOS.add(storeDTO);
        }
        shoppingCar.setStoreDTOS(storeDTOS);
        return Result.ok(shoppingCar);
    }

    @Override
    public Result deleteService(List<Integer> ids) {
        if (ids.isEmpty()){
            return Result.fail("上传数据出错");
        }
        //获取当前用户
        UserDTO userDTO = UserHolder.get();
        if (userDTO==null){
            return Result.fail("身份信息过期");
        }
       if (!deleteById(ids, userDTO.getId())){
           return Result.fail("保存失败");
       }
        return Result.ok();
    }

    @Override
    public Result carToCollectionService(List<Integer> ids) {

        //先获取
        if (ids.isEmpty()){
            return Result.fail("上传数据出错");
        }
        //获取当前用户
        UserDTO userDTO = UserHolder.get();
        if (userDTO==null){
            return Result.fail("身份信息过期");
        }
        //添加收藏表
        for (Integer id : ids) {
            int i = collectionDao.addByGoodsId(id, userDTO.getId());
            if (i <= 0) {
                return Result.fail("保存收藏失败");
            }
        }

        //删除购物车
        if (!deleteById(ids, userDTO.getId())){
            return Result.fail("删除失败");
        }
        return Result.ok();
    }

    @Override
    public Result addService(List<ShoppingCar> cars) {
        if (cars.size()<=0 || cars==null){
            return Result.fail(202,"上传参数出错");
        }
         cars.stream().map(shoppingCar -> shoppingCar.setUserId(UserHolder.get().getId()))
                .forEach(shoppingCar -> carDao.addCar(shoppingCar));

        return Result.ok();
    }


    public boolean deleteById(List<Integer> ids,Integer userId){
        for (Integer id : ids) {
            int delete = carDao.delete(userId, id);
            if (delete<=0){
                return false;
            }
        }
        return true;
    }

}
