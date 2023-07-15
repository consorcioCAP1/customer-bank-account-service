package com.nttdata.bootcamp.customerbankaccountservice.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.nttdata.bootcamp.customerbankaccountservice.documents.CustomerBankAccount;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



public interface CustomerBankAccountRepository extends ReactiveMongoRepository<CustomerBankAccount, String>{

	Flux<CustomerBankAccount> findAllByDni(String dni);
	Mono<CustomerBankAccount> findByDniAndAccountType(String dni, String accountType);
	Flux<CustomerBankAccount> findAllByBusinessName(String businessName);
}
