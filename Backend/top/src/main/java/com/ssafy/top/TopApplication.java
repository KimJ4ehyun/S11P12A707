package com.ssafy.top;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableScheduling
public class TopApplication {

	public static void main(String[] args) {
		SpringApplication.run(TopApplication.class, args);
	}

}
