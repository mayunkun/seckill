package com.aeert.seckill.service.impl;

import com.aeert.seckill.dao.OrderDao;
import com.aeert.seckill.entity.OrderEntity;
import com.aeert.seckill.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

}
