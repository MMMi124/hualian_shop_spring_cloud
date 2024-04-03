package com.mme.feignService;

import com.mme.domain.entity.Cart;
import com.mme.domain.entity.CartItem;
import com.mme.exception.SysException;
import com.mme.feignRollback.CartFeignRollback;
import com.mme.feinInterface.CartService;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author lxin
 * @description
 * @Date 2024/3/31 22:47
 */
@Service
@FeignClient(value = "eureka-provider-cart",fallback = CartFeignRollback.class) //调用的服务名
public interface CartFeignService extends CartService {

    @RequestMapping(value = "/cart/addItem",method = RequestMethod.POST)
    @Override
    Boolean addItem(@RequestParam("userId") Long userId,
                    @RequestParam("goodsId") Long goodsId,
                    @RequestParam("num") Integer num);

    @PostMapping(value = "/cart/queryAll")
    @Override
    List<CartItem> queryAll(@RequestParam("userId")Long userId);

    @PostMapping(value = "/cart/removeItem")
    @Override
    Boolean removeItem(@RequestParam("userId")Long userId,@RequestParam("goodsId")Long... goodsId);

    @PostMapping(value = "/cart/updateItemNum")
    @Override
    Boolean updateItemNum(@RequestParam("userId")Long userId,
                          @RequestParam("goodsId")Long goodsId,
                          @RequestParam("num")Integer num);

    @PostMapping(value = "/cart/clear")
    @Override
    void clear(@RequestParam("userId")Long userId);




}
