package com.hanwha.hwgi.ep.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@SpringBootApplication
public class EPBatchApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(EPBatchApplication.class);
		app.run(args);
	}
}
