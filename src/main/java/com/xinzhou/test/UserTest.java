package com.xinzhou.test;

import com.xinzhou.service.UserService;
import com.xinzhou.TakeoutBackoutApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TakeoutBackoutApplication.class)

public class UserTest {

    @Autowired
    private UserService userService;

}
