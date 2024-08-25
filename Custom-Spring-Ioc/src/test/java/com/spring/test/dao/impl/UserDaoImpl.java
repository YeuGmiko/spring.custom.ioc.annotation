package com.spring.test.dao.impl;

import com.spring.custom.annotation.Repository;
import com.spring.test.dao.UserDao;

@Repository("userDao")
public class UserDaoImpl implements UserDao {
    @Override
    public void run() {
        System.out.println("UserDao Running Success......");
    }
}
