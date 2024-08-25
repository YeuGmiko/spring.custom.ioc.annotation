package com.spring.custom.exception.beanOfInter;

public class InterfaceHasMoreImplClass extends RuntimeException{
    private String message;

    public InterfaceHasMoreImplClass(Class inter, Class[] classes) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("The interface[").append(inter.getName()).append("] has corresponded more impl class than one: ");
        for (Class clazz : classes) {
            if (inter.isAssignableFrom(clazz))
                stringBuffer.append("\n").append(clazz.getName());
        }
        this.message = stringBuffer.toString();
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
