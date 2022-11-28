package com.maidou.reggie.dto;


import com.maidou.reggie.entity.Setmeal;
import com.maidou.reggie.entity.SetmealDish;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;

@Data
@ApiModel("套餐DTO")
public class SetmealDto extends Setmeal {

    @ApiModelProperty("套餐菜品集合")
    private List<SetmealDish> setmealDishes;

    @ApiModelProperty("分类名")
    private String categoryName;
}
