package com.lywtakeout.takeout.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lywtakeout.takeout.common.CustomException;
import com.lywtakeout.takeout.common.R;
import com.lywtakeout.takeout.dto.SetmealDto;
import com.lywtakeout.takeout.entity.Setmeal;
import com.lywtakeout.takeout.entity.SetmealDish;
import com.lywtakeout.takeout.mapper.SetmealMapper;
import com.lywtakeout.takeout.service.SetmealDishService;
import com.lywtakeout.takeout.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper,Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * 新增套餐，同时需要保存套餐和菜品的关联关系
     * @param setmealDto
     */
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        //保存套餐的基本信息，操作setmeal，执行insert操作
        this.save(setmealDto);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        //保存套餐和菜品的关联信息，操作setmeal_dish,执行insert操作
        setmealDishService.saveBatch(setmealDishes);
    }

    /**
     * 删除套餐，同时需要删除套餐和菜品的关联数据
     * @param ids
     */
    @Transactional
    public void removeWithDish(List<Long> ids) {
        //select count(*) from setmeal where id in (1,2,3) and status = 1
        //查询套餐状态，确定是否可用删除
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.in(Setmeal::getId,ids);
        queryWrapper.eq(Setmeal::getStatus,1);

        int count = this.count(queryWrapper);
        if(count > 0){
            //如果不能删除，抛出一个业务异常
            throw new CustomException("套餐正在售卖中，不能删除");
        }

        //如果可以删除，先删除套餐表中的数据---setmeal
        this.removeByIds(ids);

        //delete from setmeal_dish where setmeal_id in (1,2,3)
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getSetmealId,ids);
        //删除关系表中的数据----setmeal_dish
        setmealDishService.remove(lambdaQueryWrapper);
    }

    @Override
    public R<String> sellStatus(Integer status, List<Long> ids) {
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(ids != null, Setmeal::getId, ids);

        List<Setmeal> list = setmealMapper.selectList(queryWrapper);

        for (Setmeal setmeal : list) {
            if (setmeal != null) {
                setmeal.setStatus(status);
                setmealMapper.updateById(setmeal);
            }
        }

        return R.success("售卖状态修改成功");
    }

    @Override
    public SetmealDto getData(Long id) {
        Setmeal setmeal = this.getById(id);

        SetmealDto setmealDto = new SetmealDto();

        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(id != null, SetmealDish::getSetmealId, id);
        if(setmeal != null){
            BeanUtils.copyProperties(setmeal, setmealDto);
            List<SetmealDish> list = setmealDishService.list(queryWrapper);

            setmealDto.setSetmealDishes(list);
            return setmealDto;
        }
        return null;
    }
}
