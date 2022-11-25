package com.maidou.reggie.dto;


import com.maidou.reggie.entity.Setmeal;
import com.maidou.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
