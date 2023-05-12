package com.lywtakeout.takeout.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lywtakeout.takeout.common.R;
import com.lywtakeout.takeout.dto.SetmealDto;
import com.lywtakeout.takeout.entity.Category;
import com.lywtakeout.takeout.entity.Setmeal;
import com.lywtakeout.takeout.entity.SetmealDish;
import com.lywtakeout.takeout.service.CategoryService;
import com.lywtakeout.takeout.service.SetmealDishService;
import com.lywtakeout.takeout.service.SetmealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 套餐管理
 */

@RestController
@RequestMapping("/setmeal")
@Slf4j
@Api(tags = "套餐相关接口")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SetmealDishService setmealDishService;


    //每次新增或删除将缓存中的套餐数据全部删除
    /**
     * 新增套餐
     * @param setmealDto
     * @return
     */
    @PostMapping
    @CacheEvict(value = "setmealCache",allEntries = true)
    @ApiOperation(value = "套餐新增接口")
    public R<String> save(@RequestBody SetmealDto setmealDto){
        log.info("套餐信息：{}",setmealDto);

        setmealService.saveWithDish(setmealDto);

        return R.success("新增套餐成功");
    }


    @PostMapping("/status/{status}")
    @CacheEvict(value = "setmealCache",allEntries = true)
    @ApiOperation(value = "套餐起售接口")
    public R<String> sellStatus(@PathVariable("status") Integer status, @RequestParam List<Long> ids) {
        return setmealService.sellStatus(status, ids);
    }

    /**
     * 套餐分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    @ApiOperation(value = "套餐分页查询接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true),
            @ApiImplicitParam(name = "name", value = "套餐名称", required = false)
    })
    public R<Page> page(int page,int pageSize,String name){
        //分页构造器对象
        Page<Setmeal> pageInfo = new Page<>(page,pageSize);
        Page<SetmealDto> dtoPage = new Page<>();

        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据name进行like模糊查询
        queryWrapper.like(name != null,Setmeal::getName,name);
        //添加排序条件，根据更新时间降序排列
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        setmealService.page(pageInfo,queryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo,dtoPage,"records");
        List<Setmeal> records = pageInfo.getRecords();

        List<SetmealDto> list = records.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();
            //对象拷贝
            BeanUtils.copyProperties(item,setmealDto);
            //分类id
            Long categoryId = item.getCategoryId();
            //根据分类id查询分类对象
            Category category = categoryService.getById(categoryId);
            if(category != null){
                //分类名称
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());

        dtoPage.setRecords(list);
        return R.success(dtoPage);
    }

    /**
     * 删除套餐
     * @param ids
     * @return
     */
    @CacheEvict(value = "setmealCache", allEntries = true)
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        log.info("ids:{}",ids);

        setmealService.removeWithDish(ids);

        return R.success("套餐数据删除成功");
    }


    /**
     * 根据条件查询套餐数据
     * @param setmeal
     * @return
     */
    @GetMapping("/list")
    @Cacheable(value = "setmealCache", key = "#setmeal.categoryId + '_' + #setmeal.status")
    public R<List<Setmeal>> list(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(setmeal.getCategoryId() != null,Setmeal::getCategoryId,setmeal.getCategoryId());
        queryWrapper.eq(setmeal.getStatus() != null,Setmeal::getStatus,setmeal.getStatus());
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        List<Setmeal> list = setmealService.list(queryWrapper);

        return R.success(list);
    }

    /**
     * 修改套餐信息
     * @param setmealDto
     * @return
     */
    @PutMapping
    public R<String> edit(@RequestBody SetmealDto setmealDto)
    {
        //先判断是否接收到数据
        if(setmealDto==null)
        {
            return  R.error("请求异常");
        }
        //判断套餐下面是否还有关联菜品
        if(setmealDto.getSetmealDishes()==null)
        {
            return R.error("套餐没有菜品，请添加");
        }
        //获取到关联的菜品列表，注意关联菜品的列表使我们提交过来的
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        //获取到套餐的id
        Long setmealId = setmealDto.getId();
        //构造关联菜品的条件查询
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        //根据套餐id在关联菜品中查询数据，注意这里所做的查询是在数据库中进行查询的
        queryWrapper.eq(SetmealDish::getSetmealId,setmealId);
        //关联菜品先移除掉原始数据库中的数据
        setmealDishService.remove(queryWrapper);
        //为setmeal_dish表填充相关的属性
        //这里我们需要为关联菜品的表前面的字段填充套餐的id
        for(SetmealDish setmealDish:setmealDishes)
        {
            setmealDish.setSetmealId(setmealId);//填充属性值
        }
        //批量把setmealDish保存到setmeal_dish表
        //这里我们保存了我们提交过来的关联菜品数据
        setmealDishService.saveBatch(setmealDishes);//保存套餐关联菜品
        //这里我们正常更新套餐
        setmealService.updateById(setmealDto);//保存套餐
        return R.success("套餐修改成功");
    }


    @GetMapping("/{id}")
    public R<SetmealDto> getData(@PathVariable Long id){
        SetmealDto setmealDto = setmealService.getData(id);
        return R.success(setmealDto);
    }

}
