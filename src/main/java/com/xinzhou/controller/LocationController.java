package com.xinzhou.controller;

import com.xinzhou.dto.Result;
import com.xinzhou.entity.Location;
import com.xinzhou.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private LocationService locationService;


    @GetMapping("/getLocationList")
    public Result locationList(){
        return locationService.getLocationListService();
    }

    @PostMapping("/update/id")
    public Result updateLocation(@RequestBody Location location){
        return locationService.updateService(location);
    }

    @PostMapping("/insert")
    public Result insertController(@RequestBody Location location){
        return locationService.insertService(location);
    }


}
