package com.itheima.takeout.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.takeout.entity.ShoppingCart;

import javax.servlet.http.HttpSession;

public interface ShoppingCartService extends IService<ShoppingCart> {

    public void clean(Long userId);

}
