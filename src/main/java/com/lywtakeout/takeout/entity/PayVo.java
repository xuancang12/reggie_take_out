package com.lywtakeout.takeout.entity;

import lombok.Data;

/**
 * 黄洋林: Huang
 * 2023/4/19 19:26
 */

@Data
public class PayVo {
    private String out_trade_no; // 商户订单号 必填
    private String subject; // 订单名称 必填
    private String total_amount;  // 付款金额 必填
    private String body; // 商品描述 可空
}
