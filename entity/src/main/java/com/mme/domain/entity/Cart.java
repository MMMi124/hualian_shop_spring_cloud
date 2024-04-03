package com.mme.domain.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author lxin
 * @description 购物车实体
 * @Date 2024/3/29 16:25
 */
@ApiModel("购物车实体")
@Data
public class Cart implements Serializable {

    private static final long serialVersionUID = -2082991153546452233L;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("商品列表")
    private List<CartItem> items;
}
