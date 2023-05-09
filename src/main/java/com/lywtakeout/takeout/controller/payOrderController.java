package com.lywtakeout.takeout.controller;

import com.alipay.api.AlipayApiException;
import com.lywtakeout.takeout.common.R;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 用户下单
 */
@Slf4j
@Controller
public class payOrderController {
    @Autowired
    AlipayTemplate alipayTemplate;

    @Autowired
    private OrderService orderService;

    @ResponseBody
    @PostMapping(value = "/payOrder")
    public R<String> payOrder(@RequestBody Orders orders, HttpServletRequest request,HttpSession session, HttpServletResponse response) throws AlipayApiException, IOException {
        log.info("Orders:{}", orders);
        PayVo payVo = orderService.getOrderPay(orders, session);
        String form = alipayTemplate.pay1(payVo, response);
        return R.success(form);
    }
}
