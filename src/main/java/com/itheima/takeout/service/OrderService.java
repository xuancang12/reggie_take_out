package com.itheima.takeout.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.takeout.entity.Orders;

import javax.servlet.http.HttpSession;

public interface OrderService extends IService<Orders> {

    /**
     * 用户下单
     * @param orders
     */
    public void submit(Orders orders, HttpSession session);
}
