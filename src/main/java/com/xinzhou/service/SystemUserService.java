package com.xinzhou.service;

import com.xinzhou.dto.Result;

public interface SystemUserService {
    Result adminCountService();

    Result adminAddDayCountService(String date);

    Result getListService();
}
