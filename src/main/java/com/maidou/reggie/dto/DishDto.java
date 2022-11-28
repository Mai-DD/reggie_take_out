package com.maidou.reggie.dto;

import com.maidou.reggie.entity.Dish;
import com.maidou.reggie.entity.DishFlavor;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel("菜品DTO")
public class DishDto extends Dish {

    @ApiModelProperty("菜品口味集合")
    private List<DishFlavor> flavors = new ArrayList<>();

    @ApiModelProperty("分类名")
    private String categoryName;

    private Integer copies;
}
