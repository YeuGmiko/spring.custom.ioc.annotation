package com.spring.custom.exception.beanOfInter;

public class InterfaceHasCorrespondedNoBean extends RuntimeException {
    private String message;

    public InterfaceHasCorrespondedNoBean(Class inter) {
        this.message = "The interface["+inter.getName()+"] has not corresponded any bean";
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
