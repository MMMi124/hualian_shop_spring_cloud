package com.mme.serviceImpl;

import com.mme.domain.entity.Cart;
import com.mme.domain.entity.CartItem;
import com.mme.domain.entity.TbGoods;
import com.mme.exception.ExceptionEnum;
import com.mme.exception.SysException;
import com.mme.feignService.GoodsFeignService;
import com.mme.feinInterface.CartService;
import com.mme.feinInterface.GoodsService;
import com.mme.redis.RedisConst;
import com.mme.service.TbGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lxin
 * @description
 * @Date 2024/3/31 23:02
 */
@RestController
@RequestMapping("/cart")
@Slf4j
public class CartServiceImpl implements CartService {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private GoodsFeignService goodsFeignService;

    @PostMapping("/addItem")
    @Override
    public Boolean addItem(@RequestParam("userId") Long userId,
                           @RequestParam("goodsId")Long goodsId,
                           @RequestParam("num")Integer num) {
        log.info("添加商品");
        RMap<Long, CartItem> map = redissonClient.getMap(RedisConst.CART + userId);

        for (Long key : map.keySet()) {
            if (key.equals(goodsId)) {
                CartItem item = map.get(key);
                item.setNum(item.getNum() + num);
                map.put(goodsId,item);
                log.info("用户购物车中有该商品");
                return true;
            }
        }
        //根据goodsId获取goods信息

        TbGoods goods = goodsFeignService.getOne(goodsId);
        CartItem cartItem = new CartItem();
        BeanUtils.copyProperties(goods,cartItem);
        cartItem.setNum(num);
        map.put(goodsId,cartItem);
        log.info("添加成功后用户购物车信息，map:{}",map.toString());

        return true;
    }

    @PostMapping("/removeItem")
    @Override
    public Boolean removeItem(@RequestParam("userId")Long userId,@RequestParam("goodsId") Long... goodsId) {
        log.info("批量删除");
        RMap<Long, CartItem> map = redissonClient.getMap(RedisConst.CART + userId);
        for (Long key : map.keySet()) {
            for (Long one : goodsId) {
                if ((one == key)) {
                    map.remove(key);
                }
            }
        }

        return true;
    }

    @PostMapping("/updateItemNum")
    @Override
    public Boolean updateItemNum(@RequestParam("userId") Long userId,@RequestParam("goodsId") Long goodsId,
                                 @RequestParam("num") Integer num) {
        log.info("更新购物车中一条商品信息");
        RMap<Long, CartItem> map = redissonClient.getMap(RedisConst.CART + userId);
        for (Long key : map.keySet()) {
            if ((key.equals(goodsId))) {
                CartItem cartItem = map.get(key);
                cartItem.setNum(num);
                map.put(goodsId,cartItem);
            }
        }
        return true;
    }

    @PostMapping("/queryAll")
    @Override
    public List<CartItem> queryAll(@RequestParam("userId") Long userId) {
        log.info("回显用户购物车信息");
        //在redis中查询用户id
        RMap<Long, CartItem> map = redissonClient.getMap(RedisConst.CART + userId);

        if (!map.isExists()) {
            new SysException(ExceptionEnum.CART_IS_NULL);
        }
        Cart cart = new Cart();
        cart.setUserId(userId);
        List<CartItem> list = new ArrayList<>();

        System.out.println(map.entrySet().size());
        for (Long key : map.keySet()) {
            CartItem item = map.get(key);
            list.add(item);
        }
        System.out.println(list);
        return list;
    }

    @PostMapping("/clear")
    @Override
    public void clear(@RequestParam("userId") Long userId) {
        log.info("清空购物车");
        //在redis中查询用户id
        RMap<Long, CartItem> map = redissonClient.getMap(RedisConst.CART + userId);
        map.clear();
    }

}
