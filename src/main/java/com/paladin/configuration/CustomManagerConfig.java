package com.paladin.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.paladin.framework.core.exception.CommonHandlerExceptionResolver;
import com.paladin.framework.spring.SpringBeanHelper;
import com.paladin.framework.spring.SpringContainerManager;
import com.paladin.framework.thymeleaf.TontoDialect;


@Configuration
@EnableTransactionManagement
public class CustomManagerConfig {
	
	@Bean
	public SpringContainerManager springContainerManager() {
		return new SpringContainerManager();
	}
	
	@Bean
	public SpringBeanHelper springBeanHolder() {
		return new SpringBeanHelper();
	}
	
	@Bean
	public HandlerExceptionResolver getHandlerExceptionResolver() {
		return new CommonHandlerExceptionResolver();
	}
	
	@Bean
	public TontoDialect tontoDialect() {
		return new TontoDialect();
	}
	
}
