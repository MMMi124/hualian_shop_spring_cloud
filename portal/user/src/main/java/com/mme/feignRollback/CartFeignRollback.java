package com.mme.feignRollback;

import com.mme.domain.entity.CartItem;
import com.mme.feignService.CartFeignService;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author lxin
 * @description
 * @Date 2024/3/31 23:07
 */
@Component
public class CartFeignRollback implements CartFeignService {

    @Override
    public Boolean removeItem(Long userId, Long... goodsId) {
        return null;
    }

    @Override
    public Boolean updateItemNum(Long userId, Long goodsId, Integer num) {
        return null;
    }

    @Override
    public List<CartItem> queryAll(Long userId) {
        return null;
    }

    @Override
    public void clear(Long userId) {

    }

    @Override
    public Boolean addItem(Long userId, Long goodsId, Integer num) {
        return null;
    }
}
