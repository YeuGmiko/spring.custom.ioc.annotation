package com.spring.test;

import com.spring.custom.bean.ApplicationContext;
import com.spring.custom.bean.impl.AnnotationApplicationContext;
import com.spring.test.service.UserService;
import com.spring.test.service.impl.UserServiceImpl;

public class Application {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationApplicationContext("com.spring.test");
        UserService userService = (UserService) context.getBean(UserService.class);
        userService.run();
    }
}
