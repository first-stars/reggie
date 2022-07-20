package com.w.reggie.service.impl;

import com.w.reggie.entity.AddressBook;
import com.w.reggie.mapper.AddressBookMapper;
import com.w.reggie.service.IAddressBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 地址管理 服务实现类
 * </p>
 *
 * @author xin
 * @since 2022-07-10
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements IAddressBookService {

}
