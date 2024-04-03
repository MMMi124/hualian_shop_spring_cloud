package com.mme.controller;

import com.mme.domain.entity.CartItem;
import com.mme.domain.vo.ResponseVo;
import com.mme.exception.ExceptionEnum;
import com.mme.exception.SysException;
import com.mme.feignService.CartFeignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lxin
 * @description
 * @Date 2024/3/31 22:45
 */
@RestController
@RequestMapping("/cart")
@Api("购物车")
public class CartController {

    @Autowired
    private CartFeignService cartFeignService;

    @ApiOperation("添加商品")
    @PostMapping("/addItem")
    public ResponseVo addItem(@RequestParam("userId") @ApiParam(name = "userId",value = "用户id") Long userId,
                              @RequestParam("goodsId") @ApiParam(name = "goodsId",value = "商品id") Long goodsId,
                              @RequestParam("num") @ApiParam(name = "num",value = "商品数量") Integer num) throws SysException {
        Boolean addSuc = cartFeignService.addItem(userId, goodsId, num);
        if (addSuc == null){
            throw new SysException(ExceptionEnum.SERVICE_LOST);
        }
        return addSuc ? ResponseVo.success("添加商品到购物车成功") :
                ResponseVo.failed("添加商品到购物车失败");
    }

    @ApiOperation("回显用户购物车信息")
    @PostMapping("/queryAll")
    public ResponseVo<List<CartItem>> queryAll(@RequestParam("userId") @ApiParam(name = "userId",value = "用户id") Long userId)
            throws SysException{
        List<CartItem> list = cartFeignService.queryAll(userId);
        if (list == null){
             new SysException(ExceptionEnum.SERVICE_LOST);
        }
        return ResponseVo.success("查询到购物车信息",list);
    }

    @ApiOperation("批量删除购物车商品")
    @PostMapping("/removeItem")
    public ResponseVo removeItem(@RequestParam("userId") @ApiParam(name = "userId",value = "用户id") Long userId,
                                                 @RequestParam("goodsId") @ApiParam(name = "goodsId",value = "商品编号") Long ... goodsId) throws SysException {

        Boolean removeSuc = cartFeignService.removeItem(userId, goodsId);
        if (removeSuc == null){
            new SysException(ExceptionEnum.SERVICE_LOST);
        }
        return ResponseVo.success("删除成功");
    }

    @ApiOperation("更新购物车商品")
    @PostMapping("/updateItemNum")
    public ResponseVo updateItemNum(@RequestParam("userId") @ApiParam(name = "userId",value = "用户id") Long userId,
                                    @RequestParam("goodsId") @ApiParam(name = "goodsId",value = "商品id") Long goodsId,
                                    @RequestParam("num") @ApiParam(name = "num",value = "商品数量")  Integer num){

        Boolean updateSuc = cartFeignService.updateItemNum(userId, goodsId, num);
        if (updateSuc == null){
            new SysException(ExceptionEnum.SERVICE_LOST);
        }
        return ResponseVo.success("更新成功");
    }

    @ApiOperation("清空购物车商品")
    @PostMapping("/clear")
    public ResponseVo clear(@RequestParam("userId") @ApiParam(name = "userId",value = "用户id") Long userId){

        cartFeignService.clear(userId);
        return ResponseVo.success("清空成功");
    }

}
