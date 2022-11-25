package com.maidou.reggie.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maidou.reggie.entity.AddressBook;
import com.maidou.reggie.mapper.AddressBookMapper;
import com.maidou.reggie.service.AddressBookService;
import org.springframework.stereotype.Service;

/**
 * @author MaiDou
 * @version 1.0
 * @date 2022/11/12 19:00
 **/
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
