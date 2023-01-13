package com.sonycode.compiler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.AsyncConfigurer;


@SpringBootApplication
public class CompilerApplication implements AsyncConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(CompilerApplication.class, args);
	}

}
