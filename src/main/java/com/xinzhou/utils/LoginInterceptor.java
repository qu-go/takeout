package com.xinzhou.utils;

import com.xinzhou.dto.UserDTO;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // TODO: 使用session登录校验代码
/*        //获取user
        HttpSession session = request.getSession();
       UserDTO userDTO = (UserDTO) session.getAttribute("userDTO");
        if (userDTO==null){
            response.setStatus(401);
            return false;
      }
       //保存到ThreadLocal
        UserHolder.save(userDTO);
       return true;*/

        //todo:redis登录验证
        //1.从threadLocal查找是否有用户信息
        UserDTO userDTO = UserHolder.get();
        //2.如果为空就拦截
        if (userDTO==null){
            response.setStatus(401);
            return false;
        }

        //3. 放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.remove();
    }
}
