package com.xinzhou.controller;

import com.xinzhou.dto.Result;
import com.xinzhou.entity.Goods;
import com.xinzhou.entity.GoodsType;
import com.xinzhou.entity.QueryPageBean;
import com.xinzhou.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
    @PostMapping("/getGoods")
    public Result getGoodsByQuery(@RequestBody QueryPageBean queryPageBean){
       return goodsService.getGoodsByQueryService(queryPageBean);
    }
    @PostMapping("/getGoods/byTypeId")
    public Result getGoodsByTypeId(@RequestBody QueryPageBean queryPageBean){

        return goodsService.getGoodsByTypeIdService(queryPageBean);
    }

    @GetMapping("/getGoods/ByTwoId")
    public Result getGoodsByStoreId(@RequestParam("type") Integer type, @RequestParam("store") Integer store_id){
        return goodsService.getGoodsByTypeIDAndScoreId(type,store_id);
    }

    /**
     * 根据id下架食品
     */
    @GetMapping("/down/good")
    public Result downGood(@RequestParam Integer goodId){
        Result result;
        try {
           result= goodsService.downGoodService(goodId);
        }catch (RuntimeException e){
            return Result.fail(209,e.getMessage());
        }
        return result;
    }

    /**
     * 根据id获取食品
     */
    @GetMapping("/get/id")
    public Result getGood(@RequestParam Integer goodId){
        return goodsService.getGoodById(goodId);
    }
    /**
     * 修改食品
     */
    @PostMapping("/update")
    public Result updateController(@RequestBody Goods good){
        Result result;
        try{
           result =goodsService.updateService(good);
        }catch (RuntimeException e){
            return Result.fail(202,e.getMessage());
        }
        return  result;
    }
    /**
     * 增加食品
     */
    @PostMapping("/add")
    public Result addController(@RequestBody Goods good){
        System.out.println(good);
        System.out.println(good.getGoods_store_id());
        Result result;
        try{
            result =goodsService.addService(good);
        }catch (RuntimeException e){
            return Result.fail(202,e.getMessage());
        }
        return  result;
    }

    @GetMapping("/search")
    public Result searchFood(@RequestParam("keyWord") String text){
        return goodsService.searchService(text);
    }

    @GetMapping("/count")
    public Result getFoodCount(Integer id){
        return goodsService.getFoodCountService(id);
    }

    @GetMapping("/list")
    public Result getGoodsList(Integer offset,Integer limit){
        return goodsService.getGoodsListService(offset,limit);
    }

    @GetMapping("/typeList/{id}")
    public Result getTypeList(@PathVariable("id") Integer id){
        return goodsService.getTypeService(id);
    }

    @PostMapping("/add/type")
    public Result addType(@RequestBody GoodsType goodsType){
        Result result=null;
        try{
            result = goodsService.insertType(goodsType);
        }catch (RuntimeException e){
            return Result.fail(203,e.getMessage());
        }
        return result;
    }
    @GetMapping("/allType")
    public Result getAllTypeList(){
        return goodsService.getAllTypeService();
    }
}
