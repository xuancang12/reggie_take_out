package com.itheima.takeout.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.takeout.common.R;
import com.itheima.takeout.dto.SetmealDto;
import com.itheima.takeout.entity.Setmeal;

import java.util.List;
import java.util.Set;

public interface SetmealService extends IService<Setmeal> {
    /**
     * 新增套餐，同时需要保存套餐和菜品的关联关系
     * @param setmealDto
     */
    public void saveWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐，同时需要删除套餐和菜品的关联数据
     * @param ids
     */
    public void removeWithDish(List<Long> ids);

    /**
     * 修改套餐起售，停售状态
     * @param status
     * @param ids
     * @return
     */
    public R<String> sellStatus(Integer status, List<Long> ids);

    public SetmealDto getData(Long id);
}
