package com.virkade.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.ConfigurableEnvironment;

@ComponentScan("com.virkade.cms.graphql")
@SpringBootApplication
public class BootApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(BootApplication.class, args);
		ConfigurableEnvironment env = ctx.getEnvironment();
	}

}
