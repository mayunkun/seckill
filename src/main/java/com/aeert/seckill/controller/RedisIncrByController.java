package com.aeert.seckill.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

/**
 * @Author l'amour solitaire
 * @Description redis测试
 * @Date 2020/11/4 下午4:21
 **/
@Api(tags = "redis测试")
@RestController
@RequestMapping("/redis")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RedisIncrByController {

    private final RedisTemplate redisTemplate;

    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @ApiOperation("init")
    @GetMapping("init")
    public String init(@RequestParam String key, @RequestParam Long count) {
        redisTemplate.delete(key);
        Long result = redisTemplate.opsForValue().increment(key, count);
        System.out.println(result);
        return String.valueOf(redisTemplate.opsForValue().get(key));
    }

    @ApiOperation("test")
    @GetMapping("test")
    public void test(@RequestParam String key, @RequestParam Long count, @RequestParam Integer ct) {
        for (int i = 0; i < ct; i++) {
            CompletableFuture<Long> future = CompletableFuture.supplyAsync(() -> redisTemplate.opsForValue().decrement(key, count), threadPoolTaskExecutor);
            future.whenComplete((r, e) -> {
                if (r.compareTo(Long.valueOf("0")) > 0) {
                    System.out.println("抢购成功！当前剩余库存为：" + r);
                } else {
                    System.out.println("抢购结束！");
                }
            });
        }
    }
}
