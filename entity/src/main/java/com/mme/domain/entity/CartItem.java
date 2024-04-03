package com.mme.domain.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lxin
 * @description 购物车项
 * @Date 2024/3/29 16:20
 */
@ApiModel("购物车项")
@Data
public class CartItem implements Serializable {

    private static final long serialVersionUID = -4730870171296452314L;

    @ApiModelProperty("商品编号")
    private Long goodsId;

    @ApiModelProperty("商品名")
    private String goodsTitle;

    @ApiModelProperty("单价：分")
    private Long goodsPrice;

    @ApiModelProperty("个数")
    private Integer num;

}
