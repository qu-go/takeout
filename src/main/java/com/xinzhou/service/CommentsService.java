package com.xinzhou.service;

import com.xinzhou.dto.Result;
import com.xinzhou.entity.Comment;

public interface CommentsService {
    Result getCommentByTypeId( Integer storeId,Integer page,Integer pageSize);

    Result addCommentService(Comment comment);
}
