package com.xinzhou.service;

import com.xinzhou.dto.Result;
import com.xinzhou.entity.ShoppingCar;

import java.util.List;

public interface ShoppingCarService {

    Result getGoods();

    Result deleteService(List<Integer> ids);

    Result carToCollectionService(List<Integer> ids);

    Result addService(List<ShoppingCar> cars);
}
