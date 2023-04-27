package com.lywtakeout.takeout.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lywtakeout.takeout.common.R;
import com.lywtakeout.takeout.dto.SetmealDto;
import com.lywtakeout.takeout.entity.Setmeal;

import java.util.List;

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
