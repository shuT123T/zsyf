package com.slj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.slj.domain.User;
import com.slj.service.UserService;
import com.slj.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author Mr.shu
* @description 针对表【user(用户信息)】的数据库操作Service实现
* @createDate 2022-11-17 14:20:44
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




