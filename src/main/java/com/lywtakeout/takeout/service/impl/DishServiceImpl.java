package com.lywtakeout.takeout.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lywtakeout.takeout.common.CustomException;
import com.lywtakeout.takeout.common.R;
import com.lywtakeout.takeout.dto.DishDto;
import com.lywtakeout.takeout.entity.Dish;
import com.lywtakeout.takeout.entity.DishFlavor;
import com.lywtakeout.takeout.mapper.DishFlavorMapper;
import com.lywtakeout.takeout.mapper.DishMapper;
import com.lywtakeout.takeout.service.DishFlavorService;
import com.lywtakeout.takeout.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper,Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    /**
     * 新增菜品，同时保存对应的口味数据
     * @param dishDto
     */
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        //保存菜品的基本信息到菜品表dish
        this.save(dishDto);

        Long dishId = dishDto.getId();//菜品id

        //菜品口味
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

        //保存菜品口味数据到菜品口味表dish_flavor
        dishFlavorService.saveBatch(flavors);

    }

    /**
     * 根据id查询菜品信息和对应的口味信息
     * @param id
     * @return
     */
    public DishDto getByIdWithFlavor(Long id) {
        //查询菜品基本信息，从dish表查询
        Dish dish = this.getById(id);

        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish,dishDto);

        //查询当前菜品对应的口味信息，从dish_flavor表查询
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dish.getId());
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(flavors);

        return dishDto;
    }

    @Override
    @Transactional
    public void updateWithFlavor(DishDto dishDto) {
        //更新dish表基本信息
        this.updateById(dishDto);

        //清理当前菜品对应口味数据---dish_flavor表的delete操作
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(DishFlavor::getDishId,dishDto.getId());

        dishFlavorService.remove(queryWrapper);

        //添加当前提交过来的口味数据---dish_flavor表的insert操作
        List<DishFlavor> flavors = dishDto.getFlavors();

        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);
    }

    @Override
    public R<String> sellStatus(Integer status, List<Long> ids) {
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(ids != null, Dish::getId, ids);

        List<Dish> list = dishMapper.selectList(queryWrapper);

        for (Dish dish : list) {
            if (dish != null) {
                dish.setStatus(status);
                dishMapper.updateById(dish);
            }
        }

        return R.success("售卖状态修改成功");
    }

    @Override
    @Transactional
    public R<String> deleteDish(List<Long> ids) {
            // 查菜品的状态，是否可以删除（）
            LambdaQueryWrapper<Dish> dishQueryWrapper = new LambdaQueryWrapper<>();
            dishQueryWrapper.in(Dish::getId, ids);
            dishQueryWrapper.eq(Dish::getStatus, 1);

            int count = this.count(dishQueryWrapper);
            if(count > 0) {
                throw new CustomException("菜品正在售卖中！不能删除！");
            }

            // 删除dish
            dishMapper.deleteBatchIds(ids);

            // 删除dish_flavor
            LambdaQueryWrapper<DishFlavor> dfQueryWrapper = new LambdaQueryWrapper<>();
            dfQueryWrapper.in(DishFlavor::getDishId, ids);

            dishFlavorMapper.delete(dfQueryWrapper);
            return R.success("删除菜品成功！");
        }

}
