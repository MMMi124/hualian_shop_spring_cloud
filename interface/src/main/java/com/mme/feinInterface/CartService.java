package com.mme.feinInterface;

import com.mme.domain.entity.Cart;
import com.mme.domain.entity.CartItem;

import java.util.List;

/**
 * @author lxin
 * @description
 * @Date 2024/3/31 22:46
 */
public interface CartService {

    /**
     * 添加商品到购物车
     * @param userId
     * @param goodsId
     * @param num
     * @return
     */
    Boolean addItem(Long userId,Long goodsId,Integer num);

    /**
     * 批量删除购物车商品
     * @param userId
     * @param goodsId
     * @return
     */
    Boolean removeItem(Long userId,Long... goodsId);

    /**
     * 更新购物车中某项商品数量
     * @param userId
     * @param goodsId
     * @param num
     * @return
     */
    Boolean updateItemNum(Long userId,Long goodsId,Integer num);

    /**
     * 回显用户购物车信息
     * @param userId
     * @return
     */
    List<CartItem> queryAll(Long userId);

    /**
     *清空购物车
     * @param userId
     */
    void clear(Long userId);

    /**
     * 结算选中项
     * @param userId
     * @param items
     * @return
     */
    Cart settle(Long userId, List<CartItem> items);

}
