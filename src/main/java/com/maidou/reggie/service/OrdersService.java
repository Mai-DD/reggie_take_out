package com.maidou.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maidou.reggie.entity.Orders;

/**
 * @author MaiDou
 * @version 1.0
 * @date 2022/11/13 19:00
 **/
public interface OrdersService extends IService<Orders> {
    void submit(Orders orders);
}
