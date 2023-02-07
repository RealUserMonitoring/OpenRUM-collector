package com.openrum.collector.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringBeanUtils<T> implements ApplicationContextAware {

  private static ApplicationContext applicationContext;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    SpringBeanUtils.applicationContext = applicationContext;
  }

  public static ApplicationContext  getContext() {
    return applicationContext;
  }

  public static <T> T  getBean(Class<T> clazz) {
    return applicationContext != null?applicationContext.getBean(clazz):null;
  }

  public static Object getBeanByString(String beanName) throws BeansException {
    return applicationContext.getBean(beanName);
  }

}

