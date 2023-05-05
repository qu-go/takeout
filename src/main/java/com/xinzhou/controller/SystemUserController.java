package com.xinzhou.controller;

import com.xinzhou.dto.Result;
import com.xinzhou.service.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system")
public class SystemUserController {

    @Autowired
    private SystemUserService service;
    @GetMapping("/count")
    public Result adminCount(){
        return service.adminCountService();
    }
    @GetMapping("/{date}/count")
    public Result adminAddDayCount(@PathVariable String date){
        return service.adminAddDayCountService(date);
    }
    @GetMapping("/list")
    public Result getList(){
        return service.getListService();
    }
}
