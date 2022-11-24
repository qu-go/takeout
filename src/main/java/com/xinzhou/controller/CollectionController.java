package com.xinzhou.controller;

import com.xinzhou.dto.Result;
import com.xinzhou.dto.UserDTO;
import com.xinzhou.service.CollectionService;
import com.xinzhou.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/collection")
public class CollectionController {

    @Autowired
    private CollectionService service;

    @GetMapping("/collectionList")
    public Result getListController(Integer pageNum,Integer pageSize){

        return service.getListService(pageNum,pageSize);
    }


    @GetMapping("/add/id")
    public Result addByIdController(Integer good_id){
        return service.addService(good_id);
    }

    @GetMapping("/delete/id")
    public Result deleteByIdController(Integer good_id){
        return service.deleteService(good_id);
    }
}
