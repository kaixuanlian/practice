package com.liankaixuan.annotation.handler;

import com.liankaixuan.annotation.CustomizeAutowired;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * @auther liankaixuan
 * @date 2020/5/16 9:49 下午
 */
@Component
public class MyAutowireProcessor implements BeanPostProcessor, ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//		if ("myBean".equals(beanName)) {
//			System.out.println("bean 初始化之前操作。。。。。。");
			try {
				Field[] fields = bean.getClass().getDeclaredFields();
				for (Field field : fields) {
					CustomizeAutowired annotation = field.getAnnotation(CustomizeAutowired.class);
					if (annotation != null){
						field.setAccessible(true);
						if (annotation.value().length() != 0){
							field.set(bean, applicationContext.getBean(annotation.value()));
						} else {
							field.set(bean, applicationContext.getBean(field.getType()));
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
//		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if ("myBean".equals(beanName)){
//			System.out.println("bean 初始化之后的操作===");
		}
		return bean;
	}
}
