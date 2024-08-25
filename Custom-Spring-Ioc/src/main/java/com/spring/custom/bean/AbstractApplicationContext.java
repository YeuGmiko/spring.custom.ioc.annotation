package com.spring.custom.bean;

import com.spring.custom.exception.*;
import com.spring.custom.exception.beanOfInter.BeanNameOfInterfaceCorrespondedMoreClass;
import com.spring.custom.exception.beanOfInter.InterfaceHasCorrespondedNoBean;
import com.spring.custom.exception.beanOfInter.InterfaceHasMoreImplClass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class AbstractApplicationContext implements ApplicationContext {

    protected static Map<String, List<Object>> beanFactoryForName = new HashMap<>();
    protected static Map<Class, List<BeanDefinition>> beanFactoryForClass = new HashMap<>();
    /*
    * Params:
    *   clazz: 接口或类的Class对象
    * */
    @Override
    public <T> T getBean(Class<T> clazz) {
        if (clazz.isInterface())
            // 该Class对象为接口,调用接口的处理方法
            return getImplClassFromInterfaceByClass(clazz);
        // 通过Class对象获取该Class注册的Bean集合
        List<BeanDefinition> beanDefinitions = beanFactoryForClass.get(clazz);
        if (beanDefinitions == null || beanDefinitions.isEmpty())
            // Bean集合为空,抛出错误
            throw new ClassCorrespondedNoBean(clazz);
        if (beanDefinitions.size() > 1)
            // 该Class对应多个Bean对象,抛出错误
            throw new ClassCorrespondedMoreBean(clazz, beanDefinitions);
        return (T) beanDefinitions.get(0).getBean();
    }

    /*Params:
    *   beanName: 注册Bean对象使用的BeanName
    * */
    @Override
    public Object getBean(String beanName) {
        // 通过BeanName获取Bean集合
        List<Object> beanList = beanFactoryForName.get(beanName);
        if (beanList == null || beanList.isEmpty())
            // 该BeanName注册的Bean对象集合为空,抛出错误
            throw new BeanNameCorrespondedNoBean(beanName);
        if (beanList.size() > 1)
            // 该BeanName注册的Bean对象集合为多个,抛出错误
            throw new BeanNameCorrespondedMoreBean(beanName, beanList);
        return beanList.get(0);
    }

    /*Params:
    *   beanName: 注册Bean对象时使用的BeanName
    *   clazz: 接口或类的Class对象
    * */
    @Override
    public <T> T getBean(String beanName, Class<T> clazz) {
        if (clazz.isInterface())
            return getBeanFromInterfaceByBeanNameAndClass(beanName, clazz);
        List<BeanDefinition> beanDefinitions = beanFactoryForClass.get(clazz);
        if (beanDefinitions == null && beanDefinitions.isEmpty())
            throw new ClassCorrespondedNoBean(clazz);
        Object bean = null;
        for (BeanDefinition beanDefinition : beanDefinitions) {
            if (beanDefinition.getId().equals(beanName)) {
                if (bean != null)
                    throw new BeanNameOfClassCorrespondedMoreBean(beanName, beanDefinitions);
                bean = beanDefinition.getBean();
            }
        }
        if (bean == null)
            throw new BeanNameCorrespondedNoBean(beanName);
        return (T) bean;
    }
    /*Params:
    *   clazz: 接口的Class对象
    * Description: 通过接口Class对象获取实现类Class
    * */
    private <T> T getImplClassFromInterfaceByClass(Class<T> clazz) {
        // 判断是否是接口
        if (!clazz.isInterface())
            throw new RuntimeException(clazz.getName() + "is not a interface");
        Class<T> implClass = null;
        Set<Class> keyClasses = beanFactoryForClass.keySet();
        for (Class keyClass : keyClasses) {
            // 判断该Bean的Class是否为该接口的实现类
            if (clazz.isAssignableFrom(keyClass)) {
                if (implClass != null)
                    // 重复获得实现类,抛出错误
                    throw new InterfaceHasMoreImplClass(clazz, keyClasses.toArray(new Class[0]));
                implClass = keyClass;
            }
        }
        if (implClass == null)
            // 未找到实现类,抛出错误
            throw new InterfaceHasCorrespondedNoBean(clazz);
        // 找到实现类,返回重新调用getBean(Class clazz)
        return getBean(implClass);
    }

    /*Params:
    *   beanName: 注册Bean对象时使用的BeanName
    *   clazz: 接口的Class对象
    * Description: 通过BeanName和接口的Class对象获取Bean对象
    * */
    private <T> T getBeanFromInterfaceByBeanNameAndClass(String beanName, Class<T> clazz) {
        if (!clazz.isInterface())
            // 判断是否是接口
            throw new RuntimeException(clazz.getName() + "is not a interface");
        if (beanName == null || beanName.isEmpty())
            // 判断BeanName是否为空
            throw new RuntimeException("BeanName has not be set");
        List<Object> beanList = beanFactoryForName.get(beanName);
        T result = null;
        for (Object bean : beanList) {
            // 判断该bean对象的Class类是否为该接口的实现类
            if (clazz.isAssignableFrom(bean.getClass())) {
                if (result != null)
                    throw new BeanNameOfInterfaceCorrespondedMoreClass(beanName, clazz, beanList);
                result = (T) bean;
            }
        }
        if (result == null)
            throw new BeanNameCorrespondedNoBean(beanName);
        return result;
    }
}
