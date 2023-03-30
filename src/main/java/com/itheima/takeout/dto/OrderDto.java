package com.itheima.takeout.dto;

import com.itheima.takeout.entity.OrderDetail;
import com.itheima.takeout.entity.Orders;
import lombok.Data;

import java.util.List;

/**
 * 黄洋林: Huang
 * 2023/3/30 14:14
 */
@Data
public class OrderDto extends Orders {
    private List<OrderDetail> orderDetails;
}
