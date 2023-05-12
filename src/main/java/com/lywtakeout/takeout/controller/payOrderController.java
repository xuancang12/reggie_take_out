package com.lywtakeout.takeout.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.lywtakeout.takeout.common.BaseContext;
import com.lywtakeout.takeout.common.CustomException;
import com.lywtakeout.takeout.common.R;
import com.lywtakeout.takeout.config.AlipayConfig;
import com.lywtakeout.takeout.config.AlipayTemplate;
import com.lywtakeout.takeout.entity.*;
import com.lywtakeout.takeout.mapper.OrderMapper;
import com.lywtakeout.takeout.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Console;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private DishService dishService;

    @ResponseBody
    @PostMapping(value = "/payOrder")
    public R<String> payOrder(@RequestBody Orders orders,HttpSession session, HttpServletResponse response) throws AlipayApiException, IOException {
        log.info("Orders:{}", orders);
        PayVo payVo = orderService.getOrderPay(orders, session);
        String form = alipayTemplate.pay1(payVo);
        return R.success(form);
    }

    @ResponseBody
    @PostMapping(value = "/paySuccess")
    public String paySuccessNotify(HttpServletRequest request) throws Exception {
          //  log.info("订单详情：");
          //  Map<String, String> map = new HashMap<>();
          //  Map<String, String[]> requestParams = request.getParameterMap();
          //  for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
          //    String name = iter.next();
          //    String[] values = requestParams.get(name);
          //    String valueStr = "";
          //    for (int i = 0; i < values.length; i++) {
          //        valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
          //     }
          //     map.put(name, valueStr);
          // }
          // //验证签名
          //  boolean signVerified = false;
          //// try {
          ////     signVerified = AlipaySignature.rsaCheckV1(map, alipayTemplate.getAlipay_public_key(), alipayTemplate.getCharset(), alipayTemplate.getSign_type());
          ////    } catch (com.alipay.api.AlipayApiException e) {
          ////    return;
          ////}
          //  if (true) {
          //      //获得当前用户id
          //      Long userId = BaseContext.getCurrentId();
          //      //查询当前用户的购物车数据
          //      LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
          //      wrapper.eq(ShoppingCart::getUserId, userId);
          //      //清空购物车数据
          //      shoppingCartService.remove(wrapper);
          //      //查询地址数据
          //      long orderId = (Long.parseLong(map.get("out_trade_no")));//订单号
          //      LambdaQueryWrapper<Orders> lambdaQueryWrapper = new LambdaQueryWrapper<>();
          //      lambdaQueryWrapper.eq(Orders::getId, orderId);
          //      orderMapper.selectById(orderId);
          //      Orders orders = orderService.getOne(lambdaQueryWrapper);
          //      orders.setStatus(2);
          //      orderService.updateById(orders);
          //  }
        if (request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name, request.getParameter(name));
            }
            String sign = params.get("sign");
            String content = AlipaySignature.getSignCheckContentV1(params);
            boolean checkSignature = AlipaySignature.rsa256CheckContent(content, sign, alipayTemplate.getAlipay_public_key(), "UTF-8"); // 验证签名
            // 支付宝验签
            if (checkSignature) {
                //获得当前用户id
                Long userId = BaseContext.getCurrentId();
                //查询当前用户的购物车数据
                LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(ShoppingCart::getUserId, userId);
                //清空购物车数据
                shoppingCartService.remove(wrapper);
                //查询地址数据
                Orders order = orderMapper.selectById(Long.parseLong(params.get("out_trade_no")));
                order.setStatus(2);
                orderMapper.updateById(order);
            }
        }
        return "success";
    }
}
