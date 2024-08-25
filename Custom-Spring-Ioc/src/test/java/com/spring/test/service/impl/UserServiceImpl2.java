package com.spring.test.service.impl;

import com.spring.custom.annotation.Autowired;
import com.spring.custom.annotation.Service;
import com.spring.test.dao.UserDao;
import com.spring.test.service.UserService;


public class UserServiceImpl2 implements UserService {
    @Autowired("userDao2")
    UserDao userDao;

    @Override
    public void run() {
        System.out.println("UserService Running Success......");
        userDao.run();
    }
}
