package com.itheima.takeout.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.takeout.entity.Category;

public interface CategoryService extends IService<Category> {

    public void remove(Long id);

}
