package com.maidou.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 套餐菜品关系
 */
@Data
@ApiModel("套餐菜品关系")
public class SetmealDish implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private Long id;


    @ApiModelProperty("分类id")
    private Long setmealId;


    @ApiModelProperty("菜品id")
    private Long dishId;


    @ApiModelProperty("菜品名称")
    private String name;

    @ApiModelProperty("菜品原价")
    private BigDecimal price;

    @ApiModelProperty("份数")
    private Integer copies;


    @ApiModelProperty("排序")
    private Integer sort;


    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    @ApiModelProperty("修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    @ApiModelProperty("修改人")
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;


    @ApiModelProperty("创建人")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;


    @ApiModelProperty("是否删除")
    private Integer isDeleted;
}
