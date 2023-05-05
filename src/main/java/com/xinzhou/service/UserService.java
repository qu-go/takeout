package com.xinzhou.service;

import com.xinzhou.dto.LoginFormDTO;
import com.xinzhou.dto.Result;
import com.xinzhou.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public interface UserService {


    Result login(LoginFormDTO loginFormDTO, HttpSession session);

    Result register(User user);

    Result uploadPic(MultipartFile imgFile) throws IOException;

    Result modifyPwd(LoginFormDTO loginFormDTO);

    Result logout(String token);

    Result getInfoByToken(String token);

    Result userDayCount(String date);

    Result userCountService();

    Result userListService(Integer offset, Integer limit);
}
