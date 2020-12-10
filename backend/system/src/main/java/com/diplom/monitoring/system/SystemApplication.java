package com.diplom.monitoring.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.diplom.monitoring.system")
public class SystemApplication {
	public static void main(String[] args) {
		SpringApplication.run(SystemApplication.class, args);
	}
}
