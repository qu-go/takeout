package com.xinzhou.controller;

import com.xinzhou.dto.Result;
import com.xinzhou.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentsService service;

    @GetMapping("/commentList")
    public Result getComments( Integer storeId,Integer page,Integer pageSize){

        System.out.println(storeId);
        return service.getCommentByTypeId(storeId,page,pageSize);
    }
}
