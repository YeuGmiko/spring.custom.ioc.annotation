package com.spring.custom.exception.beanOfInter;

public class InterfaceHasCorrespondedNoClass extends RuntimeException {
    private String message;

    public InterfaceHasCorrespondedNoClass(Class inter) {
        this.message = "The interface["+inter.getName()+"] has not corresponded any impl class";
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
