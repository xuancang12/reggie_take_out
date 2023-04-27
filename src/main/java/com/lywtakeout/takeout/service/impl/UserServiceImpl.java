package com.lywtakeout.takeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lywtakeout.takeout.entity.User;
import com.lywtakeout.takeout.mapper.UserMapper;
import com.lywtakeout.takeout.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService{
}
