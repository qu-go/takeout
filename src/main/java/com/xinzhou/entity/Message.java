package com.xinzhou.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data

public class Message implements Serializable {
    //信息发送的的类别
    //101表示发送给全体
    //102表示发送服务器然后转发给个体
    //103表示发送给服务器
    public Integer code;
    //0表示商家
    //1表示用户
    //2表示服务器
    public Integer senderIdentity;
    public Integer senderId;
    //接收者id
    public Integer receiverId;
    //0表示商家
    //1表示用户
    //2表示服务器
    public Integer receiverIdentity;
    //发送内容
    public String message;
    //发送事件
    public LocalDateTime sendTime;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getSenderIdentity() {
        return senderIdentity;
    }

    public void setSenderIdentity(Integer senderIdentity) {
        this.senderIdentity = senderIdentity;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    public Integer getReceiverIdentity() {
        return receiverIdentity;
    }

    public void setReceiverIdentity(Integer receiverIdentity) {
        this.receiverIdentity = receiverIdentity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }
}
