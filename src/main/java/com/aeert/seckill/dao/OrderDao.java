package com.aeert.seckill.dao;

import com.aeert.seckill.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author l'amour solitaire
 * @Description 抢购订单表
 * @Date 2020/11/9 上午9:18
 **/
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {

}
