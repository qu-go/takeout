package com.xinzhou.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class webSocketController {

    @MessageMapping("/hello")
    @SendTo("/topic/hello")
    public String  hello( String msg){
        System.out.println(msg);
        return msg;
    }
}
