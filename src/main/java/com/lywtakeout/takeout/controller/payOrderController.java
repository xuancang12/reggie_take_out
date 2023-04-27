package com.lywtakeout.takeout.controller;

import com.alipay.api.AlipayApiException;
import com.lywtakeout.takeout.config.AlipayTemplate;
import com.lywtakeout.takeout.entity.Orders;
import com.lywtakeout.takeout.entity.PayVo;
import com.lywtakeout.takeout.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 黄洋林: Huang
 * 2023/4/19 21:12
 */
@Slf4j
@Controller
public class payOrderController {
    @Autowired
    AlipayTemplate alipayTemplate;

    @Autowired
    private OrderService orderService;

    @ResponseBody
    @PostMapping(value = "/payOrder", produces = "text/html")
    public String payOrder(@RequestBody Orders orders, HttpSession session, HttpServletResponse response) throws AlipayApiException, IOException {
        log.info("Orders:{}", orders);
        PayVo payVo = orderService.getOrderPay(orders, session);
        //String pay = alipayTemplate.pay(payVo);
        String s = alipayTemplate.pay1(payVo, response);
        return s;
    }
}
