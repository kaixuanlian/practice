package com.liankaixuan.annotation.handler;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * @auther liankaixuan
 * @date 2020/5/16 6:39 下午
 */
@Component
public class CustomizeScanConfigurer implements ApplicationContextAware, BeanFactoryPostProcessor {

	private ApplicationContext applicationContext;

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
//		System.out.println(" beanFactory 后置处理开始。。。");
		// 设置自定义注入注解
		AutowiredAnnotationBeanPostProcessor autowiredBean = applicationContext.getBean(AutowiredAnnotationBeanPostProcessor.class);
		Set<Class<? extends Annotation>> autowiredAnnotationTypes = new HashSet<>();
		autowiredAnnotationTypes.add(Autowired.class);
		autowiredAnnotationTypes.add(Value.class);
//		autowiredAnnotationTypes.add(CustomizeAutowired.class);
		autowiredBean.setAutowiredAnnotationTypes(autowiredAnnotationTypes);
		// 执行自定义扫描逻辑
		CustomizeScan scan = new CustomizeScan((BeanDefinitionRegistry) beanFactory);
//		scan.setApplicationContext(applicationContext);
		scan.start();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//		System.out.println("注入 applicationContext 上下文");
		this.applicationContext = applicationContext;
	}
}
