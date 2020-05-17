package com.liankaixuan.annotation.handler;

import com.liankaixuan.annotation.CustomizeService;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @auther liankaixuan
 * @date 2020/5/16 6:46 下午
 */
public class CustomizeScan extends ClassPathBeanDefinitionScanner {

	BeanDefinitionRegistry registry;

	public CustomizeScan(BeanDefinitionRegistry registry) {
		super(registry);
		this.registry = registry;
	}

	public CustomizeScan(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
		super(registry, useDefaultFilters);
	}

	public CustomizeScan(BeanDefinitionRegistry registry, boolean useDefaultFilters, Environment environment) {
		super(registry, useDefaultFilters, environment);
	}

	public CustomizeScan(BeanDefinitionRegistry registry, boolean useDefaultFilters, Environment environment, ResourceLoader resourceLoader) {
		super(registry, useDefaultFilters, environment, resourceLoader);
	}


	public void start() {
		TypeFilter includeFilter = new AnnotationTypeFilter(CustomizeService.class);
		addIncludeFilter(includeFilter);
		this.doScan("com.liankaixuan");
	}


	private ScopeMetadataResolver scopeMetadataResolver = new AnnotationScopeMetadataResolver();

	private CustomizeGenerator beanNameGenerator = new CustomizeGenerator();


	public Set<BeanDefinitionHolder> doScan(String... basePackages){
		Set<BeanDefinitionHolder> beanDefinitions = new LinkedHashSet<>();
		for (String basePackage : basePackages) {
			Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
			for (BeanDefinition candidate : candidates) {
				ScopeMetadata scopeMetadata = this.scopeMetadataResolver.resolveScopeMetadata(candidate);
				candidate.setScope(scopeMetadata.getScopeName());
				String beanName = this.beanNameGenerator.generateBeanName(candidate, this.registry);
				if (candidate instanceof AbstractBeanDefinition) {
					postProcessBeanDefinition((AbstractBeanDefinition) candidate, beanName);
				}
				if (candidate instanceof AnnotatedBeanDefinition) {
					AnnotationConfigUtils.processCommonDefinitionAnnotations((AnnotatedBeanDefinition) candidate);
				}
				if (checkCandidate(beanName, candidate)) {
					BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(candidate, beanName);
					definitionHolder =
							MyAnnotationConfigUtils.applyScopedProxyMode(scopeMetadata, definitionHolder, this.registry);
					beanDefinitions.add(definitionHolder);
					registerBeanDefinition(definitionHolder, this.registry);
				}
			}
		}
		return beanDefinitions;
	}

	static class MyAnnotationConfigUtils{
		static BeanDefinitionHolder applyScopedProxyMode(
				ScopeMetadata metadata, BeanDefinitionHolder definition, BeanDefinitionRegistry registry) {

			ScopedProxyMode scopedProxyMode = metadata.getScopedProxyMode();
			if (scopedProxyMode.equals(ScopedProxyMode.NO)) {
				return definition;
			}
			boolean proxyTargetClass = scopedProxyMode.equals(ScopedProxyMode.TARGET_CLASS);
			return ScopedProxyUtils.createScopedProxy(definition, registry, proxyTargetClass);
		}
	}



}
