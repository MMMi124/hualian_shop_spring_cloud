package com.mme.domain.vo;

import com.mme.domain.entity.Cart;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lxin
 * @description 购物车结算vo
 * @Date 2024/4/3 14:24
 */
@ApiModel("购物车结算信息")
@Data
public class CartSettle extends Cart {

    @ApiModelProperty("总价")
    private Long totalPrice;
}
