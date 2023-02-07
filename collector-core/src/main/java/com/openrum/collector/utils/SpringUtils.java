package com.openrum.collector.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
*
*
* @description: spring context utils
*
* @author: lou renzheng
*
* @create: 2022/12/29
**/
@Component
public class SpringUtils<T> implements ApplicationContextAware {

  private static ApplicationContext applicationContext;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    SpringUtils.applicationContext = applicationContext;
  }

  public static ApplicationContext  getContext() {
    return applicationContext;
  }

  public static <T> T getBean(Class<T> clazz) {
    return applicationContext != null?applicationContext.getBean(clazz):null;
  }

  public static Object getBeanByString(String beanName) throws BeansException {
    return applicationContext.getBean(beanName);
  }

  public static DefaultListableBeanFactory getBeanFactory() {
    return (DefaultListableBeanFactory) getContext().getAutowireCapableBeanFactory();
  }

}

