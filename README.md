# 简介

该项目是本人在学习Ioc原理后，手写的最基本的**注解式单例Ioc**的实现，在实际中并没有任意实用意义，参考学习便可，不必使用！

# Context实现方式

1. AnnotationApplicationContext（目前唯一实现方式）

   > 注解式单例Ioc实现
   >
   > **Annotation**
   >
   > - @Component
   > - @Controller
   > - @Servicez
   > - @Repository
   > - @Autowired

# Bean获取方式

1. 通过Class对象获取

   > <T> T getBean(Class<T> clazz);

2. 通过BeanName获取

   > Object getBean(String beanName);

3. 通过BeanName和Class对象获取

   > <T> T getBean(String beanName, Class<T> clazz);

4. 通过接口Class对象获取实现类Bean对象

   1. 一个接口只能获取唯一的实现类Bean对象

      > 若该接口有多个实现类（使用了Ioc注解注册了Bean对象），则会抛出错误（1对多）

   2. 一个接口若有多个实现类Bean对象，可以通过传递BeanName来获取指定实现类Bean对象

      > <T> T getBean(String beanName, Class<T> clazz);

   3. 一个接口若没有实现类Bean对象，则直接抛出错误

> 注意事项

- 一个BeanName对应多个Bean对象时，还需要Class对象来确定唯一Bean对象
- 一个Class对应多个Bean对象时（通常是接口类），还需要传递BeanName来确定唯一Bean对象

# Di依赖注入

1. 通过属性值注入（目前唯一实现方式）

   > @Autowired
   >
   > @Autowired("customBeanName")

> 注意事项

- 属性注入时，若该属性类型对应多个Bean对象，则应添加BeanName来确定唯一Bean对象

  > @Autowired("customBeanName")

- 属性注入时，若该属性类型没有对应的Bean对象，则会抛出错误

# 使用-Maven

打包后获得`Custom-Spring-Ioc-1.0.0.jar`文件，放置在项目根目录lib目录中

```test
==Project
  ——lib
    +Custom-Spring-Ioc-1.0.0.jar
  ——src
    ——main
      ——java
      ——resource
    ——test
```

在pom.xml中`<dependencies>`标签中添加如下内容

```xml
<dependency>
    <groupId>com.spring.custom</groupId>
    <artifactId>Custom-Spring-Ioc</artifactId>
    <version>1.0.0</version>
    <scope>system</scope>
    <systemPath>${project.basedir}/lib/Custom-Spring-Ioc-1.0.0.jar</systemPath>
</dependency>
```

