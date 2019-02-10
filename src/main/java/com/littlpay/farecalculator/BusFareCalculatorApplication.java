package com.littlpay.farecalculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class BusFareCalculatorApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(BusFareCalculatorApplication.class, args);
		
		BusFareProcessor service = applicationContext.getBean(BusFareProcessor.class);
	    service.readInputTaps();
		
	}

}

