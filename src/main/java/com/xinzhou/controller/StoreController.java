package com.xinzhou.controller;

import com.xinzhou.dto.Result;
import com.xinzhou.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/store")
@RestController
public class StoreController {
    @Autowired
    private StoreService service;
    @GetMapping("/getTypeList")
    public Result  getTypeListController(@RequestParam("id") Integer store_id){
        return service.getStoreService(store_id);
    }

}
