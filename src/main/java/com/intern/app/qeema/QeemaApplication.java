package com.intern.app.qeema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class QeemaApplication {
	public static void main(String[] args) {
		SpringApplication.run(QeemaApplication.class, args);
	}
}
