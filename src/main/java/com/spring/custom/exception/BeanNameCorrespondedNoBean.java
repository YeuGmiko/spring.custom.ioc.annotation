package com.spring.custom.exception;

public class BeanNameCorrespondedNoBean extends RuntimeException {
    private String message;

    public BeanNameCorrespondedNoBean(String beanName) {
        this.message = "The beanName[" + beanName + "] has corresponded no bean";
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
