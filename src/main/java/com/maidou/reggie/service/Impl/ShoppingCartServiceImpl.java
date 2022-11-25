package com.maidou.reggie.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maidou.reggie.entity.ShoppingCart;
import com.maidou.reggie.mapper.ShoppingCartMapper;
import com.maidou.reggie.service.ShoppingCartService;
import org.springframework.stereotype.Service;

/**
 * @author MaiDou
 * @version 1.0
 * @date 2022/11/12 19:27
 **/
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
