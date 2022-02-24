package com.rtb.app;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableBatchProcessing
@ComponentScan({
	"com.rtb.config", 
	"com.rtb.service",
	"com.rtb.listeners",
	"com.rtb.reader",
	"com.rtb.writer",
	"com.rtb.processor",
	"com.rtb.controller",
	"com.rtb.service"
})
@EnableAsync  // will enable the asynchronous behavior
@EnableScheduling  // will enable the spring scheduler and is used to schedule a job
public class SpringBatchGradleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchGradleApplication.class, args);
	}
	
//	@Bean
//	public RestTemplate restTemplate() {
//		
//		return new RestTemplate();
//	}

}
