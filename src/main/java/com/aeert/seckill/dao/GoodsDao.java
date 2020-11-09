package com.aeert.seckill.dao;

import com.aeert.seckill.entity.GoodsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author l'amour solitaire
 * @Description 抢购商品表
 * @Date 2020/11/9 上午9:18
 **/
@Mapper
public interface GoodsDao extends BaseMapper<GoodsEntity> {

    /**
     * 秒杀减库存
     **/
    Integer secKill(Long id);

    /**
     * 秒杀支付过期返还库存
     **/
    Integer secKillBack(Long id);

}
