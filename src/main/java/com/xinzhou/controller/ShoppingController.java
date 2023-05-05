package com.xinzhou.controller;

import com.xinzhou.dto.Result;
import com.xinzhou.entity.ShoppingCar;
import com.xinzhou.service.ShoppingCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car")
public class ShoppingController {
    @Autowired
    private ShoppingCarService service;

    @GetMapping("/getCars")
    public Result getShoppingCar(){
        return service.getGoods();
    }
    @PostMapping("/delete/ids")
    public Result deleteShoppingCar(@RequestBody List<Integer> ids){
        return service.deleteService(ids);
    }
    @PostMapping("/update/collection")
    public Result carToCollection(@RequestBody List<Integer> ids){
        return service.carToCollectionService(ids);
    }
    @PostMapping("/add/car")
    public Result addCarController(@RequestBody List<ShoppingCar> cars){
        System.out.println(cars);
        return service.addService(cars);
    }
}
