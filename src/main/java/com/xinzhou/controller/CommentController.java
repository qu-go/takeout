package com.xinzhou.controller;

import com.xinzhou.dto.Result;
import com.xinzhou.entity.Comment;
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
        return service.getCommentByTypeId(storeId,page,pageSize);
    }
    @PostMapping("/add")
    public Result addComment(@RequestBody Comment comment){
        Result result;
        try{
            result=service.addCommentService(comment);
        }catch (RuntimeException e){
            return Result.fail(403,"添加评论失败");
        }
        return result;
    }
}
