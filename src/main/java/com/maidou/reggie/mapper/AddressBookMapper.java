package com.maidou.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maidou.reggie.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author MaiDou
 * @version 1.0
 * @date 2022/11/12 18:59
 **/
@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}
