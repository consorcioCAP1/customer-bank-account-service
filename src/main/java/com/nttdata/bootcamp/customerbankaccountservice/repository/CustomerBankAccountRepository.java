package com.nttdata.bootcamp.customerbankaccountservice.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import com.nttdata.bootcamp.customerbankaccountservice.documents.CustomerBankAccount;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.data.mongodb.repository.Query;


public interface CustomerBankAccountRepository extends ReactiveMongoRepository<CustomerBankAccount, String>{

	Mono<CustomerBankAccount> findByDniAndAccountType(String dni, String accountType);

	@Query("{ 'bankAccountNumber' : ?0 }")
	Mono<CustomerBankAccount> findAccountBalanceByBankAccountNumber(String bankAccountNumber);
	
	Mono<CustomerBankAccount> findByBankAccountNumber(String bankAccountNumber);
	
	Flux<CustomerBankAccount> findByRucAndAccountType(String ruc, String accountType);

}
