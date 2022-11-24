package com.xinzhou.service;

import com.xinzhou.dto.LoginFormDTO;
import com.xinzhou.dto.Result;
import com.xinzhou.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpSession;


public interface UserService {


    Result login(LoginFormDTO loginFormDTO, HttpSession session);

    Result register(User user);

    Result uploadPic(MultipartFile imgFile);

    Result modifyPwd(LoginFormDTO loginFormDTO);

    Result logout(String token);
}
