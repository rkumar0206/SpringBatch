package com.rtb.app;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableBatchProcessing
@ComponentScan({
	"com.rtb.config", 
	"com.rtb.service",
	"com.rtb.listeners",
	"com.rtb.reader",
	"com.rtb.writer",
	"com.rtb.processor",
	"com.rtb.controller"
})
@EnableAsync  // will enable the asynchronous behavior
public class SpringBatchGradleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchGradleApplication.class, args);
	}

}
