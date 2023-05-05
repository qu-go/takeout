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

        @GetMapping("/info")
        public Result getUserInfoByToken(String token){
                return userService.getInfoByToken(token);

        }
        @PostMapping("/register")
        public Result register(@RequestBody User user){
                return userService.register(user);

        }

        @PostMapping ("/upload")
        public Result uploadPic(@RequestParam("image") MultipartFile imgFile){
                Result result;
                try{
                        result=userService.uploadPic(imgFile);
                }catch (Exception e){
                        return Result.fail(303,e.getMessage());
                }
               return result;
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

        @GetMapping(value = "/add/{date}/count")
        public Result userDayCount(@PathVariable String date){
                return userService.userDayCount(date);
        }

        @GetMapping(value = "/users/count")
        public Result userCount(){
                return userService.userCountService();
        }

        @GetMapping(value = "/list")
        public Result getUserList(Integer offset,Integer limit){
                return userService.userListService(offset,limit);
        }





}
