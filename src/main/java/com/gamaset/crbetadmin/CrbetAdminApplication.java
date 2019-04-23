package com.gamaset.crbetadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@EnableAutoConfiguration
@SpringBootApplication
public class CrbetAdminApplication {

	public static void main(String[] args) {
		System.setProperty("user.timezone", "America/Sao_Paulo");
		SpringApplication.run(CrbetAdminApplication.class, args);
	}

}
