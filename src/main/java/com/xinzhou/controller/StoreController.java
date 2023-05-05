package com.xinzhou.controller;

import com.xinzhou.dto.LoginFormDTO;
import com.xinzhou.dto.Result;
import com.xinzhou.entity.Store;
import com.xinzhou.service.StoreService;
import com.xinzhou.utils.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RequestMapping("/store")
@RestController
public class StoreController {
    @Autowired
    private StoreService service;
    @GetMapping("/getTypeList")
    public Result  getTypeListController(@RequestParam("id") Integer store_id){
        return service.getStoreService(store_id);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Store store){
        return  service.addService(store);
    }

    @PostMapping("/update")
    public Result update(@RequestBody Store store){
        return  service.updateService(store);
    }

    @PostMapping("/login")
    public Result login(@RequestBody LoginFormDTO loginFormDTO, HttpSession session){

        return service.login(loginFormDTO, session);
    }

    @GetMapping("/getMe")
    public Result getMe(){
        if (UserHolder.get()==null){
            return Result.fail(404);
        }
        return Result.ok(UserHolder.get());
    }

    @GetMapping("/getInfo")
    public Result getInfo(){
        return service.getInfoService();
    }

    @PostMapping("/modify/password")
    public Result modify(@RequestBody LoginFormDTO loginFormDTO){
        Result result;
        try{
            result = service.modifyPwd(loginFormDTO);
        }catch (RuntimeException e){
            return  Result.fail(422,e.getMessage());
        }
        return result;
    }

    @GetMapping("/logout")
    public Result logoutController(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        return service.logout(token);
    }

    @PostMapping ("/upload")
    public Result uploadPic(@RequestParam("image") MultipartFile imgFile,@RequestParam("flag") Integer flag){
        Result result;
        try{
            result=service.uploadPic(imgFile,flag);
        }catch (Exception e){
            return Result.fail(303,e.getMessage());
        }
        return result;
    }
    @PostMapping ("/upload2")
    public Result uploadPic( @PathVariable("imgFile")MultipartFile imgFile){
        Result result;
        System.out.println(imgFile.getSize());
        try{
            result=service.uploadPic(imgFile,1);
        }catch (Exception e){
            return Result.fail(303,e.getMessage());
        }
        return result;
    }
    @GetMapping("/count")
    public Result getStoreCount(){
        return service.countService();
    }


    @GetMapping("/list")
    public Result getStoreList(Integer offset,Integer limit){
        return service.getStoreByCondition(offset,limit);
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteStore(@PathVariable("id") Integer id){
        Result result=null;
        try {
            result = service.deleteService(id);
        }catch (Exception e){
            return Result.fail(303,"删除失败");
        }
        return result;
    }

    @GetMapping("/detail/{id}")
    public Result getDetail(@PathVariable("id") Integer id){
        return service.getStoreService(id);
    }

    @PostMapping("/insert")
    public Result insertStore(@RequestBody Store store){
        return service.insertService(store);
    }
}
