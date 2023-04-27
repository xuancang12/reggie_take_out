package com.lywtakeout.takeout.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lywtakeout.takeout.entity.ShoppingCart;

public interface ShoppingCartService extends IService<ShoppingCart> {

    public void clean(Long userId);

}
