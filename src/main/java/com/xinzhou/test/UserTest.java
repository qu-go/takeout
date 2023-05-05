package com.xinzhou.test;

import com.xinzhou.config.FTPConfig;
import com.xinzhou.dao.OrderDao;
import com.xinzhou.entity.Order;
import com.xinzhou.service.GoodsService;
import com.xinzhou.service.UserService;
import com.xinzhou.TakeoutBackoutApplication;
import com.xinzhou.utils.UserHolder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.function.Consumer;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TakeoutBackoutApplication.class)

public class UserTest {

    @Autowired
    private UserService userService;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private GoodsService goodsService;
    public boolean isPalindrome(String s) {
        if(s ==null) return true;
        StringBuilder str=new StringBuilder();
        // for(char ch : s.toCharArray()){
        //     // if(!Character.isSpace(ch)){
        //     //     str.append(ch);
        //     // }
        //     if(ch != '\0'){
        //         str.append(ch);
        //     }
        // }
        for(int i=0;i<s.length();i++){
            if(Character.isLetterOrDigit(s.charAt(i))){
                str.append(s.charAt(i));
            }
        }

        char[] nums=str.toString().toLowerCase().trim().toCharArray();
        System.out.println(nums);
        for(int i=0,j=nums.length-1;i<=j;i++,j--){

            if(nums[i] != nums[j]){
                return false;
            }
        }
        return true;
    }
    @Test
    public void test(){
        System.out.println(LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN));
        List<Order> orders = orderDao.selectAllBySellerId(3);
        int day = 0;
        int week=0;
        int month=0;
        //统计今天的订单
       for (int i=0;i< orders.size();i++){
           Order order = orders.get(i);
           if (order.getStatus()!=6){
               continue;
           }
           if (order.getCreate_time().isAfter(LocalDateTime.now().with(LocalTime.MIN)) ) {
               day++;
           }
           if(order.getCreate_time().isAfter(LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN))){
               month++;
           }
           if(order.getCreate_time().isAfter(LocalDateTime.now().with(LocalTime.MIN).plusDays(-7))){
               System.out.println(order.getCreate_time().isAfter(LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN)) );
               week++;
           }
       }
        System.out.println(day);
        System.out.println(week);
        System.out.println(month);
    }

    @Test
    public void testContains(){
        System.out.println(userService.userDayCount("2023-03-27"));
    }

    @Test
    public void test6(){
        System.out.println(goodsService.getTypeService(2));
    }
    @Test
   public void config(){
        System.out.println(FTPConfig.host);
    }

}
