package com.lywtakeout.takeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lywtakeout.takeout.entity.AddressBook;
import com.lywtakeout.takeout.mapper.AddressBookMapper;
import com.lywtakeout.takeout.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {

}
