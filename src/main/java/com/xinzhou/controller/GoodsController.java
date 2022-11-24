package com.xinzhou.controller;

import com.xinzhou.dto.Result;
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

}
