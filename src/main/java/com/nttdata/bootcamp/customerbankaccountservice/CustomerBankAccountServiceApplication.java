package com.nttdata.bootcamp.customerbankaccountservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class CustomerBankAccountServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerBankAccountServiceApplication.class, args);
	}

}
