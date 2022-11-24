package com.xinzhou.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.xinzhou.dao.CommentDao;
import com.xinzhou.dao.GoodsDao;
import com.xinzhou.dao.StoreDao;
import com.xinzhou.dao.UserDao;
import com.xinzhou.dto.Result;
import com.xinzhou.dto.UserDTO;
import com.xinzhou.entity.*;
import com.xinzhou.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommentServiceImpl implements CommentsService {

    @Autowired
    private CommentDao dao;
    @Autowired
    private StoreDao storeDao;
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private UserDao userDao;

    @Override
    public Result getCommentByTypeId(Integer storeId, Integer page, Integer pageSize) {
        CommentResponse commentResponse = new CommentResponse();
        //先查找店铺的名字
        Store store = storeDao.selectOneById(storeId);
        commentResponse.setStore_name(store.getStore_name());
        commentResponse.setId(storeId);
        //查找该店铺的评论
        List<Comment> comments = dao.selectByQuery(storeId, page*10, pageSize);
        if (comments.size()<=0){
            return Result.ok("暂无数据");
        }
        //赋值名字
        List<Comment> collect = comments.stream()
                .map(comment -> comment.setGoods_name(goodsDao.selectOne(comment.getGood_id()).getGoods_name()))
                .collect(Collectors.toList());
        //查询用户的信息
        List<Comment> collect2 = collect.stream()
                .map(comment -> comment.setUserDTO(BeanUtil.copyProperties(userDao.selectById(comment.getUser_id()), UserDTO.class)))
                .collect(Collectors.toList());
        commentResponse.setCommentList(collect2);
        return Result.ok(commentResponse,comments.size());
    }
}
