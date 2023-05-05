package com.xinzhou.service;

import com.xinzhou.dto.LoginFormDTO;
import com.xinzhou.dto.Result;
import com.xinzhou.entity.Store;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

public interface StoreService {
    Result getStoreService(Integer store_id);

    Result addService(Store store);

    Result login(LoginFormDTO loginFormDTO, HttpSession session);

    Result getInfoService();

    Result modifyPwd(LoginFormDTO loginFormDTO);

    Result logout(String token);

    Result uploadPic(MultipartFile imgFile,Integer flag) throws Exception;

    Result countService();

    Result getStoreByCondition(Integer offset, Integer limit);

    Result updateService(Store store);

    Result deleteService(Integer id);

    Result insertService(Store store);
}
