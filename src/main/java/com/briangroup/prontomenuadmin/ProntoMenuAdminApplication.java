package com.briangroup.prontomenuadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ProntoMenuAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProntoMenuAdminApplication.class, args);
	}

}
