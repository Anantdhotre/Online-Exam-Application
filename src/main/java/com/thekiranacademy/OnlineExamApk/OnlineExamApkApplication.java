package com.thekiranacademy.OnlineExamApk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("com")
@EntityScan("com")
public class OnlineExamApkApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineExamApkApplication.class, args);
	
	System.out.println("Application Started");
	
	}


}

