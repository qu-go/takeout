package com.xinzhou.controller;


import com.xinzhou.dto.LoginFormDTO;
import com.xinzhou.dto.Result;
import com.xinzhou.dto.UserDTO;
import com.xinzhou.entity.User;
import com.xinzhou.service.UserService;
import com.xinzhou.utils.FtpUtils;
import com.xinzhou.utils.QiniuUtils;
import com.xinzhou.utils.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

        @Autowired
        private UserService userService;

        @PostMapping("/login")
        public Result login(@RequestBody LoginFormDTO loginFormDTO, HttpSession session){
                return userService.login(loginFormDTO, session);
        }


        @PostMapping("/register")
        public Result register(@RequestBody User user){
                return userService.register(user);

        }

        @PostMapping ("/upload")
        public Result uploadPic(@RequestParam("image") MultipartFile imgFile){
               return userService.uploadPic(imgFile);
        }

        @GetMapping("/getMe")
        public Result getMe(){
                UserDTO userDTO = UserHolder.get();
                if (userDTO!=null){
                        return Result.ok(userDTO);
                }
                return Result.fail("获取失败,请重新登录");
        }
        @PostMapping("/modify/password")
        public Result modify(@RequestBody LoginFormDTO loginFormDTO){
                return userService.modifyPwd(loginFormDTO);
        }

        @GetMapping("/logout")
        public Result logoutController(HttpServletRequest request){
                String token = request.getHeader("Authorization");
                return userService.logout(token);
        }


}
