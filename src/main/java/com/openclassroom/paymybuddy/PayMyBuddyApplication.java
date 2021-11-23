package com.openclassroom.paymybuddy;

import com.openclassroom.paymybuddy.annotation.ExcludeFromJacocoGeneratedReport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@ExcludeFromJacocoGeneratedReport
public class PayMyBuddyApplication {

	public static void main(String[] args) {
		SpringApplication.run(PayMyBuddyApplication.class, args);
	}

}
