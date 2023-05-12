package com.lywtakeout.takeout.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lywtakeout.takeout.entity.Orders;
import com.lywtakeout.takeout.entity.PayVo;

import javax.servlet.http.HttpSession;

public interface OrderService extends IService<Orders> {

    /**
     * 用户下单
     * @param orders
     */
    //public void submit(Orders orders, HttpSession session);

    public PayVo getOrderPay(Orders orders, HttpSession session);

}
