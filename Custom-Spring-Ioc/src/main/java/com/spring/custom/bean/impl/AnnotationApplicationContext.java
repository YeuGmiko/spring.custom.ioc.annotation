package com.spring.custom.bean.impl;

import com.spring.custom.annotation.*;
import com.spring.custom.bean.AbstractApplicationContext;
import com.spring.custom.bean.BeanDefinition;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;

public class AnnotationApplicationContext extends AbstractApplicationContext {
    // 所支持的注解列表
    private static final List<Class> supportedAnnotation = new ArrayList<>(Arrays.asList(new Class[]{
            Component.class, Controller.class, Service.class, Repository.class
    }));
    private static String rootPath;
    public AnnotationApplicationContext(String basePackage) {
        // 获取包路径
        String packagePath = basePackage.replaceAll("\\.", "/");
        try {
            // 获取资源路径
            Enumeration<URL> resources = this.getClass().getClassLoader().getResources(packagePath);
            while (resources.hasMoreElements()) {
                URL element = resources.nextElement();
                // 得到资源绝对路径
                String filePath = URLDecoder.decode(element.getFile(), "utf-8");
                rootPath = filePath.substring(0, filePath.length() - packagePath.length());
                // 初始化BeanFactory
                loadBean(new File(filePath));
                // BeanFactory注入依赖
                loadDi();
            }
        } catch (IOException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                 InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static void loadBean(File file) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // 非文件夹直接返回
        if (!file.isDirectory())
            return;
        // 获取当前目录下所有文件
        File[] childrenFiles = file.listFiles();
        for (File children : childrenFiles) {
            // 文件夹递归遍历
            if (children.isDirectory()) {
                loadBean(children);
                continue;
            }
            // 判断是否为.class文件
            if (!children.getName().endsWith(".class"))
                continue;
            // 获取文件class路径和class名称
            String classPath = children.getAbsolutePath() // Output: com.spring.test.Application.class
                    .substring(rootPath.length() - 1)
                    .replaceAll("\\\\", "\\.");
            String fullName = classPath.substring(0, classPath.lastIndexOf(".class")); // Output: com.spring.test.Application
            Class clazz = Class.forName(fullName);
            String beanName = "";
            try {
                // 判断是否不为接口 | 含有@Component注解或@Component派生注解
                if (clazz.isInterface() || (beanName = getBeanName(clazz)) == null)
                    continue;
            } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            // 获取实例化对象
            Object bean = clazz.getConstructor().newInstance();
            BeanDefinition beanDefinition = new BeanDefinition(beanName, bean);
            // 储存以beanName维护的Map集合
            try {
                beanFactoryForName.get(beanName).add(bean);
            } catch (NullPointerException e) {
                beanFactoryForName.put(beanName, new ArrayList<>(Arrays.asList(bean)));
            }
            // 储存以Class维护的Map集合
            try {
                beanFactoryForClass.get(clazz).add(beanDefinition);
            } catch (NullPointerException e) {
                beanFactoryForClass.put(clazz, Collections.singletonList(beanDefinition));
            }
        }
    }

    private void loadDi() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        for (List<Object> beanList : beanFactoryForName.values()) {
            for (Object bean : beanList) {
                for (Field field : bean.getClass().getDeclaredFields()) {
                    // 判断是否使用@Autowired
                    Autowired anno = field.getDeclaredAnnotation(Autowired.class);
                    if (anno == null)
                        continue;
                    String beanName = anno.value();
                    Class clazz = field.getType();
                    // 设置私有属性注入权限
                    field.setAccessible(true);
                    // 判断注入方式
                    if (beanName.isEmpty())
                        // 根据Class进行注入
                        field.set(bean, getBean(clazz));
                    else
                        // 根据beanName进行注入
                        field.set(bean, getBean(beanName));
                }
            }
        }
    }


    // 获取并返回注册的BeanName,没有指定注解则返回null
    private static String getBeanName(Class clazz) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        for (Annotation annotation : clazz.getAnnotations()) {
            if (supportedAnnotation.contains(annotation.annotationType())) {
                String beanName = (String) annotation.getClass().getDeclaredMethod("value").invoke(annotation);
                return beanName.isEmpty() ? clazz.getSimpleName().substring(0, 1).toLowerCase() + clazz.getSimpleName().substring(1) : beanName;
            }
        }
        return null;
    }
}
