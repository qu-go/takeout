package com.xinzhou.service;

import com.xinzhou.dto.Result;
import com.xinzhou.entity.Location;

public interface LocationService {
    Result getLocationListService();

    Result updateService(Location location);

    Result insertService(Location location);
}
