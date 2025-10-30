package com.inditex.testJava2025;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.inditex")
public class TestJava2025Application {

	public static void main(String[] args) {
		SpringApplication.run(TestJava2025Application.class, args);
	}

}
