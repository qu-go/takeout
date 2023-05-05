package com.xinzhou.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.RandomUtil;
import com.xinzhou.dto.LoginFormDTO;
import com.xinzhou.dto.Result;
import com.xinzhou.dto.UserDTO;
import com.xinzhou.entity.User;
import com.xinzhou.dao.UserDao;
import com.xinzhou.service.UserService;
import com.xinzhou.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public Result login(LoginFormDTO loginFormDTO, HttpSession session) {
        //验证手机号码格式
        String phone = loginFormDTO.getPhone();
        if (RegexUtils.isPhoneInvalid(phone)){
            return Result.fail(302, ResultConstant.PHONE_INVALID);
        }
        //查找是否存在此用户
        User user = userDao.selectOne(loginFormDTO);

        //不存在返回信息
        if (user == null) {
            return Result.fail(301,ResultConstant.USER_NOT_EXIST);
        }
        //存在，将user信息转化为不敏感得userDTO
        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
       Map<String,Object> userDTOMap= BeanUtil.
               beanToMap(userDTO,new HashMap<>(), CopyOptions.create()
                .setIgnoreNullValue(true).setFieldValueEditor((fieldName,fieldValue) -> fieldValue!=null ? fieldValue.toString():""));

        //生成token
        String  token= UUID.randomUUID().toString();

        //保存token和userDTOMap信息
        stringRedisTemplate.opsForHash().putAll(RedisConstant.LOGIN_TOKEN_KEY+token, userDTOMap);
        //设置token的过期时间
        stringRedisTemplate.expire(RedisConstant.LOGIN_TOKEN_KEY+token, RedisConstant.LOGIN_TOKEN_EXPIRE_TIME, TimeUnit.MINUTES);
        return Result.ok(200,token);
    }

    @Override
    public Result register(User user) {
        String phone = user.getPhone();
        if (RegexUtils.isPhoneInvalid(phone)){
            return Result.fail(302, ResultConstant.PHONE_INVALID);
        }
        //根据手机号查找用户
        int num = userDao.selectByPhone(phone);
        System.out.println(num);
        //如果有用户直接返回信息
        if (num>0){
            return Result.fail(303,ResultConstant.EXISTED_USER);
        }
        //如果没有信息就创建用户
        boolean flag = createUser(user);
        if (!flag){
            return Result.fail(304,"添加用户失败");
        }
        //返回信息
        return Result.ok("创建用户成功");
    }

    @Override
    public Result uploadPic(MultipartFile imgFile) throws IOException {
        String picUrl = CommonUtil.getPicUrl(imgFile);
        boolean b = saveIcon(picUrl);
        if (!b){
            throw new RuntimeException("保存图片失败");
        }
        return Result.ok(picUrl);
    }

    @Override
    public Result modifyPwd(LoginFormDTO loginFormDTO) {
        UserDTO userDTO = UserHolder.get();
        if (userDTO==null){
           return Result.fail(402,"重新登录");
        }
        if (userDao.selectByIdAndPassword(userDTO.getId(), loginFormDTO.getPhone())<=0) {
           return Result.fail(412,"密码错误");
        }
        if (userDao.modifyPw(userDTO.getId(), loginFormDTO.getPassword())<=0) {
            return Result.fail(422,"修改失败");
        }
        return Result.ok();
    }

    @Override
    public Result logout(String token) {
        UserDTO userDTO = UserHolder.get();
        if (userDTO==null){
            return Result.ok();
        }
        //如果有数据就删除
        stringRedisTemplate.delete(RedisConstant.LOGIN_TOKEN_KEY+token);
        UserHolder.remove();
        return Result.ok();
    }

    @Override
    public Result getInfoByToken(String token) {
        if (token.length()<=0){
            return Result.fail(402,"token格式错误");
        }
        Map<Object, Object> userMap = stringRedisTemplate.opsForHash().entries(RedisConstant.LOGIN_TOKEN_KEY + token);
        if (userMap.isEmpty()){
            return Result.fail(403,"token失效");
        }
        UserDTO userDTO = BeanUtil.fillBeanWithMap(userMap, new UserDTO(), false);
        // ['admin'] or ,['developer','editor']
        String[] roles = new String[]{"admin"};
        userDTO.setRoles(roles);
        return Result.ok(200,userDTO);
    }

    @Override
    public Result userDayCount(String date) {
        if (StringUtils.isEmpty(date)){
            return Result.fail(420,"日期不能为空");
        }
        int result = userDao.getNewInsertUser(CommonUtil.getStartDay(date), CommonUtil.getNextDay(date));
        return Result.ok(200,result);
    }

    @Override
    public Result userCountService() {
        int res= userDao.selectAll();
        return Result.ok(200,res);
    }

    @Override
    public Result userListService(Integer offset, Integer limit) {
        UserDTO userDTO = UserHolder.get();
        if (userDTO==null){
            return Result.fail(402,"重新登录");
        }
        List<User> users =userDao.selectList(offset,limit);
        List<UserDTO> res = users.stream().map(user -> BeanUtil.copyProperties(user, UserDTO.class)).collect(Collectors.toList());
        return Result.ok(200,res);
    }



    private boolean createUser(User user) {
        user.setIcon(ResultConstant.DEFAULT_ICON);
        user.setNick_name(ResultConstant.DEFAULT_NICK_NAME_PREFIX+ RandomUtil.randomString(5));
        int flag = userDao.save(user);
        return flag > 0;

    }

    //把照片信息保存在个人的数据icon
    public boolean saveIcon(String fileName){
        UserDTO userDTO = UserHolder.get();
        if (userDTO==null){
            return false;
        }
        userDTO.setIcon(fileName);
        int result=userDao.saveIconByUserDTO(userDTO);
        return result>0;
    }
}
