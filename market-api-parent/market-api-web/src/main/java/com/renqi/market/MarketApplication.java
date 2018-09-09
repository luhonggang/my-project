package com.renqi.market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 配置启动类
 * @author luhonggang
 */
@SpringBootApplication(scanBasePackages ={"com.renqi.market"})
@EnableTransactionManagement
public class MarketApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(MarketApplication.class, args);
	}

	@Bean(name = "taskExecutor")
	public ThreadPoolTaskExecutor getTaskExecutor(){
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(1);
		taskExecutor.setMaxPoolSize(2);
		taskExecutor.setQueueCapacity(300);
		taskExecutor.setKeepAliveSeconds(120);
		taskExecutor.initialize();
		return taskExecutor;
	}
	@Bean
	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
		PropertySourcesPlaceholderConfigurer c = new PropertySourcesPlaceholderConfigurer();
		c.setIgnoreUnresolvablePlaceholders(true);
		return c;
	}
}
