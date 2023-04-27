package com.lywtakeout.takeout.dto;

import com.lywtakeout.takeout.entity.Setmeal;
import com.lywtakeout.takeout.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
