package com.spring.custom.exception;

import com.spring.custom.bean.BeanDefinition;

import java.util.List;

public class ClassCorrespondedMoreBean extends RuntimeException {
    private String message;
    public ClassCorrespondedMoreBean(Class clazz, List<BeanDefinition> beanList) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("The class[").append(clazz.getName()).append("] has corresponded many bean than one:");
        for (BeanDefinition definition : beanList) {
            stringBuffer.append("\n").append(definition.getBean().getClass().getName());
        }
        this.message = stringBuffer.toString();
    }
    @Override
    public String getMessage() {
        return this.message;
    }
}
