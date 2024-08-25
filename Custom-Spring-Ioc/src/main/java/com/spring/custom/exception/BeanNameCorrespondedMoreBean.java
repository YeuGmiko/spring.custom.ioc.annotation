package com.spring.custom.exception;

import java.util.List;

public class BeanNameCorrespondedMoreBean extends RuntimeException {
    private String message;
    public BeanNameCorrespondedMoreBean(String beanName, List<Object> beanList) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("The beanName[").append(beanName).append("] has corresponded many bean than one:");
        for (Object obj : beanList) {
            stringBuffer.append("\n").append(obj.getClass().getName());
        }
        this.message = stringBuffer.toString();
    }
    @Override
    public String getMessage() {
        return this.message;
    }
}
