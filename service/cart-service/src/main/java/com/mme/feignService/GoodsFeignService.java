package com.mme.feignService;

import com.mme.domain.entity.TbGoods;
import com.mme.feinInterface.GoodsService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author lxin
 * @description 调用商品模块服务
 * @Date 2024/4/3 14:08
 */
@Service
@FeignClient(value = "eureka-provider-goods",fallback = GoodsFeignRollback.class) //调用的服务名
public interface GoodsFeignService extends GoodsService {

    @PostMapping("/goods/getOne")
    @Override
    TbGoods getOne(@RequestParam("goodsId")Long goodsId);
}

class GoodsFeignRollback implements GoodsFeignService{

    @Override
    public TbGoods getOne(Long goodsId) {
        return null;
    }
}