package com.aeert.seckill.service.impl;

import com.aeert.seckill.entity.GoodsEntity;
import com.aeert.seckill.entity.OrderEntity;
import com.aeert.seckill.service.GoodsService;
import com.aeert.seckill.service.OrderService;
import com.aeert.seckill.service.SecKillService;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service("secKillService")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SecKillServiceImpl implements SecKillService {
    private final RedisTemplate redisTemplate;
    private final GoodsService goodsService;
    private final OrderService orderService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GoodsEntity initGoods(String name, Integer amount, BigDecimal price) {
        GoodsEntity goodsEntity = new GoodsEntity().setName(name).setAmount(amount).setPrice(price).setStartDate(new Date()).setEndDate(new Date());
        Assert.isTrue(goodsService.save(goodsEntity), "抢购商品初始化发生异常～");
        // 缓存库存
        redisTemplate.opsForValue().increment("amount:" + goodsEntity.getId(), amount);
        // 缓存商品信息
        redisTemplate.opsForValue().set("goods:" + goodsEntity.getId(), goodsEntity);
        return goodsEntity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean secKill(String key) {
        Long result = redisTemplate.opsForValue().decrement("amount:" + key, 1);
        if (result.compareTo(0L) >= 0) {
            // 下面的数据库操作建议走MQ让数据库按照他的处理能力，从消息队列中拿取消息进行处理。
            Try.of(() -> {
                Assert.isTrue(goodsService.secKill(Long.valueOf(key)), "库存不足！");
                Assert.isTrue(orderService.save(new OrderEntity().setGoodsId(Long.valueOf(key)).setOrderNo(UUID.randomUUID().toString().replace("-", ""))), "订单创建发生异常～");
                return true;
            }).onFailure((e) -> {
                log.error("持久化异常：" + e.getMessage());
                redisTemplate.opsForValue().increment("amount:" + key, 1);
            });
            return false;
        }
        redisTemplate.opsForValue().increment("amount:" + key, 1);
        return false;
    }
}
