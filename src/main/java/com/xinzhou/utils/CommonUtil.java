package com.xinzhou.utils;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.xinzhou.config.FTPConfig;
import com.xinzhou.dto.Result;
import com.xinzhou.dto.UserDTO;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

@Component
@Data
public class CommonUtil {

    //拼接存储在concurrentHashMap的key用来获取session
    public static  String splitKey(String userId,String identity){
        if (StringUtils.isEmpty(userId)||StringUtils.isEmpty(identity)){
            return "";
        }
        String key=userId+":"+identity;
        return key.trim();
    }

    public static boolean isUseDtoNull(UserDTO userDTO){
        if (userDTO==null) {
            return true;
        }
        return false;
    }

    public static String get16UUID(){
        // 1.开头两位，标识业务代码或机器代码（可变参数）
        String machineId = "11";
        // 2.中间四位整数，标识日期
        SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
        String dayTime = sdf.format(new Date());
        // 3.生成uuid的hashCode值
        int hashCode = UUID.randomUUID().toString().hashCode();
        // 4.可能为负数
        if(hashCode < 0){
            hashCode = -hashCode;
        }
        // 5.算法处理: 0-代表前面补充0; 10-代表长度为10; d-代表参数为正数型
        String value = machineId + dayTime + String.format("%010d", hashCode);
        return value;
    }

    /**
     * 获取上传照片的路径
     */

    public static String getPicUrl(MultipartFile imgFile) throws IOException {
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
        FtpUtils.uploadFile(FTPConfig.host, FTPConfig.port, FTPConfig.userName, FTPConfig.passWord, FTPConfig.basePath, FTPConfig.baseUrl, fileName, inputStream);
        return  FTPConfig.baseUrl+fileName;
    }

    /**
     * 通过字符串获得下一天得localdatetime
     *
     */
    public static LocalDateTime getNextDay(String date){
        DateTimeFormatter df =DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate parse = LocalDate.parse(date, df);
        return parse.plusDays(1).atStartOfDay();
    }
    /**
     * 通过字符串获得开始得时间得localdatetime
     *
     */
    public static LocalDateTime getStartDay(String date){
        DateTimeFormatter df =DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate parse = LocalDate.parse(date, df);
        return parse.atStartOfDay();
    }
}
