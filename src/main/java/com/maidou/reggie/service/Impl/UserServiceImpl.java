package com.maidou.reggie.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maidou.reggie.entity.User;
import com.maidou.reggie.mapper.UserMapper;
import com.maidou.reggie.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author MaiDou
 * @version 1.0
 * @date 2022/11/12 17:04
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
