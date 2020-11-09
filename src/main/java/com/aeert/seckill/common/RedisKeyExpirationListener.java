package com.aeert.seckill.common;

import com.aeert.seckill.entity.OrderEntity;
import com.aeert.seckill.service.GoodsService;
import com.aeert.seckill.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @Author l'amour solitaire
 * @Description 秒杀key监听
 * @Date 2020/11/9 下午1:41
 **/
@Component
@DependsOn(value = {"orderService"})
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    @Autowired
    private OrderService orderService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private RedisTemplate redisTemplate;

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    /**
     * 针对redis数据失效事件，进行数据处理
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onMessage(Message message, byte[] pattern) {
        // 用户做自己的业务处理即可,注意message.toString()可以获取失效的key
        String expiredKey = message.toString();
        if (expiredKey.startsWith("secKill:")) {
            String key = expiredKey.split(":")[1];
            OrderEntity orderEntity = orderService.getOne(new QueryWrapper<OrderEntity>().lambda().eq(OrderEntity::getId, key));
            Assert.notNull(orderEntity, "订单信息不存在！");
            Try.of(() -> {
                if (orderService.updateById(orderEntity.setStatus(3))) {
                    goodsService.secKillBack(orderEntity.getGoodsId());
                    return redisTemplate.opsForValue().increment("amount:" + orderEntity.getGoodsId(), 1);
                }
                return null;
            }).onFailure((e) -> redisTemplate.opsForValue().increment("amount:" + orderEntity.getGoodsId(), 1));
        }
    }
}