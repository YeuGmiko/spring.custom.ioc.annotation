package com.spring.test.dao.impl;

import com.spring.custom.annotation.Repository;
import com.spring.test.dao.UserDao;

@Repository("userDao2")
public class UserDaoImpl2 implements UserDao {
    @Override
    public void run() {
        System.out.println("UserDao Running Success......");
    }
}
