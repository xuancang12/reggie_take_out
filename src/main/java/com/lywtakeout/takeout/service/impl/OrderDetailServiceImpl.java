package com.lywtakeout.takeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lywtakeout.takeout.entity.OrderDetail;
import com.lywtakeout.takeout.mapper.OrderDetailMapper;
import com.lywtakeout.takeout.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}