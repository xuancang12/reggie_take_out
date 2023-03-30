package com.itheima.takeout.dto;

import com.itheima.takeout.entity.Setmeal;
import com.itheima.takeout.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
