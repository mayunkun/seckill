package com.aeert.seckill.service;

import com.aeert.seckill.entity.GoodsEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Author l'amour solitaire
 * @Description 抢购商品表
 * @Date 2020/11/9 上午9:19
 **/
public interface GoodsService extends IService<GoodsEntity> {

    /**
     * 秒杀减库存
     **/
    boolean secKill(Long id);

    /**
     * 秒杀支付过期返还库存
     **/
    boolean secKillBack(Long id);

}

