package com.spring.custom.exception;

public class ClassCorrespondedNoBean extends RuntimeException{
    private String message;

    public ClassCorrespondedNoBean(Class clazz) {
        this.message = "The class[" + clazz.getName() + "] has corresponded no bean";
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
