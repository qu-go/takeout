package com.xinzhou.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.xinzhou.dao.GoodsDao;
import com.xinzhou.dao.OrderDao;
import com.xinzhou.dao.StoreDao;
import com.xinzhou.dto.*;
import com.xinzhou.entity.*;
import com.xinzhou.service.StoreService;
import com.xinzhou.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Transactional
public class StoreServiceImpl implements StoreService {
    @Autowired
    private StoreDao storeDao;
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private OrderDao orderDao;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public Result getStoreService(Integer store_id) {
        if (store_id==null || store_id<=0){
            store_id = UserHolder.get().getId();
        }
        Store store = getStoreByIdService(store_id);
        if (store==null){
            return Result.fail("查询错误");
        }
        return Result.ok(store);
    }

    @Override
    public Result addService(Store store) {
        //再次验证store
        if (store==null || RegexUtils.isPhoneInvalid(store.getStore_phone())){
            return Result.fail(202,"上传数据出错");
        }
        //根据手机号码查询是否存在该用户
        if (storeDao.selectByPhone(store.getStore_phone())>0){
            return Result.fail(303,"该用户存在");
        }
        //添加商品用户
        storeDao.addOne(store);
        return Result.ok();
    }

    @Override
    public Result login(LoginFormDTO loginFormDTO, HttpSession session) {
        //验证手机号码格式
        String phone = loginFormDTO.getPhone();
        if (RegexUtils.isPhoneInvalid(phone)){
            return Result.fail(302, ResultConstant.PHONE_INVALID);
        }
        //查找是否存在此用户
        Store store = storeDao.selectOneByPhone(phone);
        //不存在返回信息
        if (store == null) {
            return Result.fail(301,ResultConstant.USER_NOT_EXIST);
        }
        //存在，将user信息转化为不敏感得userDTO
        UserDTO userDTO=new UserDTO();
        userDTO.setIcon(store.getStore_icon());
        userDTO.setId(store.id);
        userDTO.setNick_name(store.getStore_name());
        userDTO.setIcon(store.getStore_icon());

        //生成token
        String  token= UUID.randomUUID().toString();
        Map<String,Object> userDTOMap= BeanUtil.
                beanToMap(userDTO,new HashMap<>(), CopyOptions.create()
                        .setIgnoreNullValue(true).setFieldValueEditor((fieldName,fieldValue) -> fieldValue!=null ? fieldValue.toString():""));
        //保存token和userDTOMap信息
        stringRedisTemplate.opsForHash().putAll(RedisConstant.LOGIN_TOKEN_KEY+token, userDTOMap);
        //设置token的过期时间
        stringRedisTemplate.expire(RedisConstant.LOGIN_TOKEN_KEY+token, RedisConstant.LOGIN_TOKEN_EXPIRE_TIME, TimeUnit.MINUTES);
        return Result.ok(200,token);
    }

    @Override
    public Result getInfoService() {
        if (UserHolder.get()==null){
            return Result.fail(404);
        }
        StoreInfoDTO storeInfoDTO=new StoreInfoDTO();
        //查询store
        Store store = storeDao.selectOneById(UserHolder.get().getId());
        StoreDTO storeDTO;
        if (store!=null){
            storeDTO= BeanUtil.copyProperties(store, StoreDTO.class);
            storeInfoDTO.setStoredto(storeDTO);
        }else{
            return Result.fail(202,"查找商品为空");
        }
        //查询该店铺的订单情况
        List<Order> orders = orderDao.selectAllBySellerId(UserHolder.get().getId());
        System.out.println(orders);
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
        storeInfoDTO.setDaySale(day);
        storeInfoDTO.setWeekSale(week);
        storeInfoDTO.setMonthSale(month);

        return Result.ok(storeInfoDTO);
    }

    @Override
    public Result modifyPwd(LoginFormDTO loginFormDTO) {
        UserDTO userDTO = UserHolder.get();
        if (userDTO==null){
            return Result.fail(402,"重新登录");
        }
        if (storeDao.selectByIdAndPassword(userDTO.getId(), loginFormDTO.getPhone())<=0) {
            return Result.fail(412,"密码错误");
        }
        if (storeDao.modifyPw(userDTO.getId(), loginFormDTO.getPassword())<=0) {
            throw new RuntimeException("修改失败");

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
    public Result uploadPic(MultipartFile imgFile,Integer flag) throws Exception {
        String picUrl = CommonUtil.getPicUrl(imgFile);
        if (flag==1){
            return Result.ok(picUrl);
        }
        boolean b = saveIcon(picUrl);
        if (!b){
            throw new RuntimeException("保存图片失败");
        }
        return Result.ok(picUrl);
    }

    @Override
    public Result countService() {
        int res= storeDao.getAllCount();
        return Result.ok(200,res);
    }

    @Override
    public Result getStoreByCondition(Integer offset, Integer limit) {
        List<Store> stores = storeDao.getList(offset,limit);
        LocalDateTime time = LocalDateTime.now().minusDays(15);
        List<StoreDTO> res = stores.stream().
                map(store -> BeanUtil.copyProperties(store, StoreDTO.class).setSales(orderDao.selectOrderBySellerId(store.getId(),time))).
                collect(Collectors.toList());
        return Result.ok(200, res);
    }

    @Override
    public Result updateService(Store store) {
        UserDTO userDTO = UserHolder.get();
        if (userDTO==null){
            return Result.fail(402,"重新登录");
        }
        Integer res = storeDao.updateStore(store);
        if (res>0){
            return Result.ok(200,res);
        }
        return Result.fail(490,"更新失败");
    }

    @Override
    public Result deleteService(Integer id) {
        UserDTO userDTO = UserHolder.get();
        if (userDTO==null){
            return Result.fail(402,"重新登录");
        }
        //查询是否有该数据
        Store store = storeDao.selectOneById(id);
        if (store==null){
            return Result.fail(302,"没有该数据");
        }
        if (storeDao.deleteById(id) <=0){
            throw new RuntimeException();
        }
        return Result.ok(200);
    }

    @Override
    public Result insertService(Store store) {
        return null;
    }


    //把照片信息保存在个人的数据icon
    public boolean saveIcon(String fileName){
        UserDTO userDTO = UserHolder.get();
        if (userDTO==null){
            return false;
        }
        userDTO.setIcon(fileName);
        int result=storeDao.saveIconByUserDTO(userDTO);
        return result>0;
    }
    private Store getStoreByIdService(Integer id){
        Store store = storeDao.selectOneById(id);
        List<GoodsType> goodsTypes= storeDao.selectStoreTypeList(id);
        if (store!=null&&goodsTypes!=null){
            for (GoodsType goodsType : goodsTypes) {
                //根据store——id和type——id查找不同类型的商品
                List<Goods> goodListByType = goodsDao.getGoodsListByTwoId(goodsType.getId(), id);
                goodsType.setGoodsList(goodListByType);
            }
            store.setGoodsTypes(goodsTypes);
        }


        return store;
    }
}
