package com.spring.custom.bean;

public class BeanDefinition <T> {
    private String id;
    private T bean;

    public String getId() {
        return id;
    }

    public T getBean() {
        return bean;
    }

    public BeanDefinition(String id, T bean) {
        this.id = id;
        this.bean = bean;
    }
}
