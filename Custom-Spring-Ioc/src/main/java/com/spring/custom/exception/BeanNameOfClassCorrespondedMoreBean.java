package com.spring.custom.exception;

import com.spring.custom.bean.BeanDefinition;

import java.util.List;

public class BeanNameOfClassCorrespondedMoreBean extends RuntimeException {
    private String message;
    public BeanNameOfClassCorrespondedMoreBean(String beanName, List<BeanDefinition> beanList) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("The beanName[").append(beanName).append("] has corresponded many bean than one:");
        for (BeanDefinition beanDefinition : beanList) {
            if (beanDefinition.getId().equals(beanName))
                stringBuffer.append("\n").append(beanDefinition.getClass().getName());
        }
        this.message = stringBuffer.toString();
    }
    @Override
    public String getMessage() {
        return this.message;
    }
}
