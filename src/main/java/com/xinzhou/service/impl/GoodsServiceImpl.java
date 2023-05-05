package com.xinzhou.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.xinzhou.dao.GoodsDao;
import com.xinzhou.dao.GoodsTypeDao;
import com.xinzhou.dao.StoreDao;
import com.xinzhou.dto.Result;
import com.xinzhou.dto.SearchInfoDTO;
import com.xinzhou.dto.StoreDTO;
import com.xinzhou.entity.Goods;
import com.xinzhou.entity.GoodsType;
import com.xinzhou.entity.QueryPageBean;
import com.xinzhou.entity.Store;
import com.xinzhou.service.GoodsService;
import com.xinzhou.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    public GoodsDao dao;
    @Autowired
    public GoodsTypeDao typeDao;
    @Autowired
    public StoreDao storeDao;
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
        List<Goods> goods = dao.getGoodsByTypeId(Integer.parseInt(queryPageBean.getQuery()), queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        if (goods!=null&& goods.size()>0){
            return Result.ok(goods,goods.size());
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

    @Override
    public Result downGoodService(Integer goodId) {
        if (UserHolder.get()==null){
            return Result.fail(202);
        }
        if (dao.deleteOneById(goodId)<=0){
            throw new RuntimeException("删除失败");
        }
        return Result.ok();
    }

    @Override
    public Result getGoodById(Integer goodId) {
        if (UserHolder.get()==null){
            return Result.fail(202);
        }
        Goods goods = dao.selectOne(goodId);
        List<Goods> goodsList=new ArrayList<>();
        goodsList.add(goods);
        return goods==null ? Result.fail(204,"没有食品") : Result.ok(goodsList
        );
    }

    @Override
    public Result updateService(Goods good) {
        if (UserHolder.get()==null){
            return Result.fail(202);
        }
        return dao.updateGood(good)<=0 ? Result.fail(205,"修改失败") : Result.ok();

    }

    @Override
    public Result addService(Goods good) {
        if (UserHolder.get()==null){
            return Result.fail(202);
        }
        if (good.getGoods_store_id() <=0 && good.getGoods_type()<=0){
            good.setGoods_store_id(UserHolder.get().getId());
            int type=good.getGoods_type();
            good.setGoods_type(++type);
        }
        if (dao.addOne(good)<=0 ){
            throw new RuntimeException("添加失败");
        }
        return Result.ok();
    }

    /**
     * 查询关键字匹配的信息
     * @param keyWord
     * @return
     */
    @Override
    public Result searchService(String keyWord) {
        List<Goods> goods = dao.selectByKeyWord(keyWord);
        List<Store> stores=storeDao.selectNameByKeyWord(keyWord);
        if (goods.size() <=0 && stores.size() <=0){
            return Result.fail(101,"没有匹配数据");
        }
        SearchInfoDTO searchInfoDTO = new SearchInfoDTO();
        searchInfoDTO.setGoods(goods);
        //将store转化为storeDTO
        if (stores!=null && stores.size() >=0){
            searchInfoDTO.setStore(stores.stream().map(store -> BeanUtil.copyProperties(store, StoreDTO.class))
                    .collect(Collectors.toList()));
        }
        int goodNum = (goods.isEmpty() ? 0 : goods.size()) + (stores.isEmpty() ?  0 : stores.size());
        searchInfoDTO.setTotal(goodNum);
        return Result.ok(searchInfoDTO);
    }

    @Override
    public Result getFoodCountService(Integer id) {
        if (id==0){
            return Result.ok(200,dao.getAllCount());
        }
        int res = dao.getStoreFoodCount(id);
        return Result.ok(200,res);
    }

    @Override
    public Result getGoodsListService(Integer offset, Integer limit) {

        List<Goods> goods = dao.getList(offset,limit);
        List<Goods> res = goods.stream()
                .map(goods1 -> goods1.setStoreDTO(BeanUtil.copyProperties
                                (storeDao.selectOneById(goods1.goods_store_id), StoreDTO.class))
                        .setType(typeDao.getTypeById(goods1.getGoods_type())))
                .collect(Collectors.toList());
        return Result.ok(200,res);
    }

    @Override
    public Result getTypeService(Integer id) {
        return Result.ok(200,dao.typeList(id));
    }

    @Override
    public Result insertType(GoodsType goodsType) {
        Integer integer = typeDao.insertOne(goodsType);
        if (integer<=0) throw new RuntimeException("增加失败");
        return Result.ok();
    }

    @Override
    public Result getAllTypeService() {
        return Result.ok(200,typeDao.selectAllType());
    }

}
