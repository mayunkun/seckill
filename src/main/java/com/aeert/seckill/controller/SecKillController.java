package com.aeert.seckill.controller;

import com.aeert.seckill.service.SecKillService;
import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

/**
 * @Author l'amour solitaire
 * @Description 秒杀测试
 * @Date 2020/11/4 下午4:21
 **/
@Api(tags = "秒杀测试")
@RestController
@RequestMapping("/secKill")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SecKillController {

    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;
    private final SecKillService secKillService;

    @ApiOperation("init")
    @GetMapping("init")
    public R init(@RequestParam String name, @RequestParam Integer amount, @RequestParam BigDecimal price) {
        return R.ok(secKillService.initGoods(name, amount, price));
    }

    @ApiOperation("secKill")
    @GetMapping("secKill")
    public void secKill(@RequestParam String key, @RequestParam Integer ct) {
        for (int i = 0; i < ct; i++) {
            CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(() -> secKillService.secKill(key), threadPoolTaskExecutor);
            future.whenComplete((r, e) -> {
                if (r) {
                    System.out.println("抢购成功！");
                } else {
                    System.out.println("抢购结束！");
                }
            });
        }
    }
}
