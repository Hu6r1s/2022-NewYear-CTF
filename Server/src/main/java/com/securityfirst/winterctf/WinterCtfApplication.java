package com.securityfirst.winterctf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
public class WinterCtfApplication {

	public static void main(String[] args) {
		SpringApplication.run(WinterCtfApplication.class, args);
	}

}
