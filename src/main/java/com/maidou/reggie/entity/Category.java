package com.maidou.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 分类
 */
@Data
@ApiModel("分类")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private Long id;


    //类型 1 菜品分类 2 套餐分类
    @ApiModelProperty("类型")
    private Integer type;


    //分类名称
    @ApiModelProperty("分类名称")
    private String name;


    //顺序
    @ApiModelProperty("顺序")
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


    //是否删除
    @ApiModelProperty("是否删除")
    @TableField(exist = false)
    private Integer isDeleted;

}
