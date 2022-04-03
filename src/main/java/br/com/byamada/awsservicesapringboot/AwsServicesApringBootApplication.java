package br.com.byamada.awsservicesapringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AwsServicesApringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwsServicesApringBootApplication.class, args);
	}

}
