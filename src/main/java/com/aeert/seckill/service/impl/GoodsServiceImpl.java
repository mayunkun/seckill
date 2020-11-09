package com.aeert.seckill.service.impl;

import com.aeert.seckill.dao.GoodsDao;
import com.aeert.seckill.entity.GoodsEntity;
import com.aeert.seckill.service.GoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service("goodsService")
public class GoodsServiceImpl extends ServiceImpl<GoodsDao, GoodsEntity> implements GoodsService {

    @Override
    public boolean secKill(Long id) {
        return retBool(baseMapper.secKill(id));
    }

    @Override
    public boolean secKillBack(Long id) {
        return retBool(baseMapper.secKillBack(id));
    }
}
