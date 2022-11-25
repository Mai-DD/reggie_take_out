package com.maidou.reggie.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maidou.reggie.entity.DishFlavor;
import com.maidou.reggie.mapper.DishFlavorMapper;
import com.maidou.reggie.service.DishFlavorService;
import org.springframework.stereotype.Service;

/**
 * @author MaiDou
 * @version 1.0
 * @date 2022/11/9 13:59
 **/
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
