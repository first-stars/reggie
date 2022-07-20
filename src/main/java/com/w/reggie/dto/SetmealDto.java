package com.w.reggie.dto;

import com.w.reggie.entity.Setmeal;
import com.w.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
