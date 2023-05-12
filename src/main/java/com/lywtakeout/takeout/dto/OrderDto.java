package com.lywtakeout.takeout.dto;

import com.lywtakeout.takeout.entity.OrderDetail;
import com.lywtakeout.takeout.entity.Orders;
import lombok.Data;

import java.util.List;

/**
 * 黄洋林: Huang
 * 2023/3/30 14:14
 */
@Data
public class OrderDto extends Orders {
    private List<OrderDetail> orderDetails;
    private String dishName;
}
