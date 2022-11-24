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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {
    @Value("${FTP.ADDRESS}")
    private String host;
    // 端口
    @Value("${FTP.PORT}")
    private int port;
    // ftp用户名
    @Value("${FTP.USERNAME}")
    private String userName;
    // ftp用户密码
    @Value("${FTP.PASSWORD}")
    private String passWord;
    // 文件在服务器端保存的主目录
    @Value("${FTP.BASEPATH}")
    private String basePath;
    // 访问图片时的基础url
    @Value("${IMAGE.BASE.URL}")
    private String baseUrl;

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
       Map<String,Object> userDTOMap= BeanUtil.beanToMap(userDTO,new HashMap<>(), CopyOptions.create()
                .setIgnoreNullValue(true).setFieldValueEditor((fieldName,fieldValue) -> fieldValue.toString()));
        //生成token
        String  token= UUID.randomUUID().toString();

        //保存token和userDTOMap信息
        stringRedisTemplate.opsForHash().putAll(RedisConstant.LOGIN_TOKEN_KEY+token, userDTOMap);
        //设置token的过期时间
        stringRedisTemplate.expire(RedisConstant.LOGIN_TOKEN_KEY+token, RedisConstant.LOGIN_TOKEN_EXPIRE_TIME, TimeUnit.MINUTES);
        return Result.ok(token);
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
    public Result uploadPic(MultipartFile imgFile) {
        try {
            //获取图片名
            String originalFilename = imgFile.getOriginalFilename();
            //获取最后得点位置
            int i = originalFilename.lastIndexOf('.');
            String substring = originalFilename.substring(i);
            //使用uuid产生名称防止重复
            String fileName= UUID.randomUUID()+ substring;
            InputStream inputStream = imgFile.getInputStream();
            //上传成功存在名字在redis大集合
            //jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES, fileName);
            FtpUtils.uploadFile(host, port, userName, passWord, basePath, baseUrl, fileName, inputStream);
            boolean flag = saveIcon(baseUrl + fileName);
            System.out.println(flag);
            if (!flag){
                return Result.fail(403,"保存图片失败");
            }
            return Result.ok(baseUrl+fileName);

        }catch (Exception e){
            System.out.println("fail");
            return Result.fail(403,"保存图片失败");
        }


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
        System.out.println(userDTO);
        userDTO.setIcon(fileName);
        int result=userDao.saveIconByUserDTO(userDTO);
        return result>0;
    }
}
