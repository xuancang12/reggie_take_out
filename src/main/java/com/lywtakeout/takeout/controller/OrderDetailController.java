package com.lywtakeout.takeout.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lywtakeout.takeout.common.R;
import com.lywtakeout.takeout.entity.AddressBook;
import com.lywtakeout.takeout.entity.OrderDetail;
import com.lywtakeout.takeout.service.OrderDetailService;
import com.lywtakeout.takeout.service.OrderService;
import jdk.nashorn.internal.ir.ReturnNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单明细
 */
@Slf4j
@RestController
@RequestMapping("/orderDetail")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private OrderService orderService;


    /**
     * 根据id查询用户订单详情信息
     */
    @GetMapping("/{id}")
    public R get(@PathVariable Long id) {
        LambdaQueryWrapper<OrderDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderDetail::getOrderId, id);
        List<OrderDetail> orderDetailList = orderDetailService.list(queryWrapper);
        StringBuilder dishName = null;
        for(OrderDetail orderDetail: orderDetailList){
           dishName.append(orderDetail.getName() + orderDetail.getNumber());
        }
        return R.success(dishName);
    }

}