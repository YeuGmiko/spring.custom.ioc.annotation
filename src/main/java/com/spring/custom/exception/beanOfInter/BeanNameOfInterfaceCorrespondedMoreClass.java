package com.spring.custom.exception.beanOfInter;

import java.util.List;

public class BeanNameOfInterfaceCorrespondedMoreClass extends RuntimeException {
    private String message;
    public BeanNameOfInterfaceCorrespondedMoreClass(String beanName, Class clazz, List<Object> beanList) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("The beanName[").append(beanName).append("] of class[").append(clazz.getName()).append("] has more beans than one: ");
        for (Object bean : beanList) {
            if (clazz.isAssignableFrom(bean.getClass()))
                stringBuffer.append("\n").append(bean.getClass().getName());
        }
        this.message = stringBuffer.toString();
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
