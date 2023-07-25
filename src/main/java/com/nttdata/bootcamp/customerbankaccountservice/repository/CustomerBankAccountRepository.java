package com.nttdata.bootcamp.customerbankaccountservice.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import com.nttdata.bootcamp.customerbankaccountservice.documents.CustomerBankAccount;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerBankAccountRepository extends ReactiveMongoRepository<CustomerBankAccount, String>{

	Flux<CustomerBankAccount> findByNumberDocumentAndAccountType(String numberDocument, String accountType);
	
	Mono<CustomerBankAccount> findByBankAccountNumber(String bankAccountNumber);
	
	Flux<CustomerBankAccount> findByNumberDocument(String numberDocument);
}
