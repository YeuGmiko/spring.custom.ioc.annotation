package com.spring.custom.bean;

public interface ApplicationContext {
    public <T> T getBean(Class<T> clazz);

    public Object getBean(String beanName);

    public <T> T getBean(String beanName, Class<T> clazz);
}
