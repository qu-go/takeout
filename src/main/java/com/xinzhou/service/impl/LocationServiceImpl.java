package com.xinzhou.service.impl;

import com.xinzhou.dao.LocationDao;
import com.xinzhou.dao.UserDao;
import com.xinzhou.dto.Result;
import com.xinzhou.dto.UserDTO;
import com.xinzhou.entity.Location;
import com.xinzhou.entity.User;
import com.xinzhou.service.LocationService;
import com.xinzhou.utils.CommonUtil;
import com.xinzhou.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationDao dao;
    @Autowired
    private UserDao userDao;
    @Override
    public Result getLocationListService() {
        UserDTO userDTO = UserHolder.get();
        if (userDTO==null) {
           return Result.fail(401,"请重新登录");
        }
        List<Location> locations=dao.selectAllBy(userDTO.getId());
        if (locations.size()<=0){
            Result.ok("暂无数据");
        }
        User user = userDao.selectById(userDTO.getId());
        locations.stream().map(location -> location.setIcon(user.getIcon()))
                    .collect(Collectors.toList());
        return Result.ok(locations, locations.size());
    }

    @Override
    public Result updateService(Location location) {
        UserDTO userDTO = UserHolder.get();
        if (CommonUtil.isUseDtoNull(userDTO)) {
            return Result.fail(401,"请重新登录");
        }
        if (location.getStatus()==1){
            //先把状态都变成默认
            dao.updateStatusDefault(userDTO.getId());
        }
        int i = dao.updateByLocation(userDTO.getId(), location);

        return i >0 ? Result.ok() : Result.fail(202,"修改失败") ;
    }

    @Override
    public Result insertService(Location location) {
        UserDTO userDTO = UserHolder.get();
        if (CommonUtil.isUseDtoNull(userDTO)) {
            return Result.fail(401,"请重新登录");
        }
        System.out.println(CommonUtil.isUseDtoNull(userDTO));
        if (location.getStatus()==1){
            //先把状态都变成默认
            dao.updateStatusDefault(userDTO.getId());
        }
        location.setUser_id(userDTO.getId());
        return dao.insert(location) >0 ? Result.ok() : Result.fail(202,"添加失败");
    }
}
