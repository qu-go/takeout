package com.xinzhou.takeoutbackout;

import com.xinzhou.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class TakeoutBackoutApplicationTests {

    @Autowired
    public OrderService orderService;
    @Test
    void contextLoads() {
    }

    @Test
    public void test1() throws IOException {
        orderService.updateStatusService(74,4);
    }
}
