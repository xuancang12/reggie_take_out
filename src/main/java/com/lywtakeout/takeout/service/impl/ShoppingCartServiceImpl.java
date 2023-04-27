package com.lywtakeout.takeout.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lywtakeout.takeout.entity.ShoppingCart;
import com.lywtakeout.takeout.mapper.ShoppingCartMapper;
import com.lywtakeout.takeout.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Override
    public void clean(Long userId) {
        //查询当前用户的购物车数据
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId,userId);
        //清空购物车数据
        shoppingCartService.remove(wrapper);

    }
}
