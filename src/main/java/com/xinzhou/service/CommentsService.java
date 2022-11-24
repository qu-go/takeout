package com.xinzhou.service;

import com.xinzhou.dto.Result;

public interface CommentsService {
    Result getCommentByTypeId( Integer storeId,Integer page,Integer pageSize);
}
