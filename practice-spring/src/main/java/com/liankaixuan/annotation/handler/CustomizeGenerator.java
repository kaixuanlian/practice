package com.liankaixuan.annotation.handler;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Set;

/**
 * 自定义别名处理器
 * @auther liankaixuan
 * @date 2020/5/16 8:40 下午
 */
public class CustomizeGenerator extends AnnotationBeanNameGenerator {
	public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
		if (definition instanceof AnnotatedBeanDefinition) {
			String beanName = this.getBeanName((AnnotatedBeanDefinition) definition);
			if (StringUtils.hasText(beanName)) {
				// Explicit bean name found.
				return beanName;
			}
		}
		// Fallback: generate a unique default bean name.
		return buildDefaultBeanName(definition, registry);
	}


	/**
	 * 获取beanName
	 * @param annotatedDef
	 * @return
	 */
	String getBeanName(AnnotatedBeanDefinition annotatedDef) {
		AnnotationMetadata amd = annotatedDef.getMetadata();
		Set<String> types = amd.getAnnotationTypes();
		String beanName = null;
		for (String type : types) {
			AnnotationAttributes attributes = this.attributesFor(amd, type);
			// 判断是否为自定义的注解，以及注解中是否配置了value值
			if (attributes != null && isCustomizeAnnotationAndHasValue(type, amd.getMetaAnnotationTypes(type), attributes)) {
				Object value = attributes.get("value");
				if (value instanceof String) {
					String strVal = (String) value;
					if (StringUtils.hasLength(strVal)) {
						if (beanName != null && !strVal.equals(beanName)) {
							throw new IllegalStateException("Stereotype annotations suggest inconsistent " +
									"component names: '" + beanName + "' versus '" + strVal + "'");
						}
						beanName = strVal;
					}
				}
			}
		}
		return beanName;
	}

	/**
	 * 获取到注解配置的信息
	 * @param metadata
	 * @param annotationClassName
	 * @return
	 */
	private AnnotationAttributes attributesFor(AnnotatedTypeMetadata metadata, String annotationClassName) {

		return AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(annotationClassName, false));
	}

	boolean isCustomizeAnnotationAndHasValue(String annotationType,
												Set<String> metaAnnotationTypes, @Nullable Map<String, Object> attributes) {

		boolean isStereotype = annotationType.equals("com.liankaixuan.annotation.CustomizeService");

		return (isStereotype && attributes != null && attributes.containsKey("value"));
	}
}
