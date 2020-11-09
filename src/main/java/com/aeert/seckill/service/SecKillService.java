package com.aeert.seckill.service;

import com.aeert.seckill.entity.GoodsEntity;

import java.math.BigDecimal;

/**
 * @Author l'amour solitaire
 * @Description 秒杀服务
 * @Date 2020/11/9 上午9:36
 **/
public interface SecKillService {

    /**
     * 初始化商品信息
     **/
    GoodsEntity initGoods(String name, Integer amount, BigDecimal price);

    /**
     * 秒杀
     **/
    Boolean secKill(String key);
}
