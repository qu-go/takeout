package com.xinzhou.service.impl;

import com.xinzhou.dao.SystemUserDao;
import com.xinzhou.dto.Result;
import com.xinzhou.dto.UserDTO;
import com.xinzhou.entity.SystemUser;
import com.xinzhou.service.SystemUserService;
import com.xinzhou.utils.CommonUtil;
import com.xinzhou.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemUserServiceImpl implements SystemUserService {
    @Autowired
    private SystemUserDao dao;
    @Override
    public Result adminCountService() {
        UserDTO userDTO = UserHolder.get();
        if (userDTO==null){
            return Result.fail(404,"请重新登录");
        }
        return Result.ok(200,dao.selectCount());
    }

    @Override
    public Result adminAddDayCountService(String date) {
        UserDTO userDTO = UserHolder.get();
        if (userDTO==null){
            return Result.fail(404,"请重新登录");
        }
        return Result.ok(200,dao.getNewAdd(CommonUtil.getStartDay(date),CommonUtil.getNextDay(date)));
    }

    @Override
    public Result getListService() {
        UserDTO userDTO = UserHolder.get();
        if (userDTO==null){
            return Result.fail(404,"请重新登录");
        }
        List<SystemUser> users= dao.getList();
        if (users==null||users.isEmpty()){
            return Result.fail(490,"暂时还没有管理员信息");
        }
        return Result.ok(200,users);
    }
}
