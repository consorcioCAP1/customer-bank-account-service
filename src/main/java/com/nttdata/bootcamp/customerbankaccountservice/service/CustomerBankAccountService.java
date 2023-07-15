package com.nttdata.bootcamp.customerbankaccountservice.service;

import com.nttdata.bootcamp.customerbankaccountservice.documents.CustomerBankAccount;
import com.nttdata.bootcamp.customerbankaccountservice.dto.CustomerBankAccountDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerBankAccountService {

	public Mono<CustomerBankAccount> saveCustomerAccount(CustomerBankAccountDto customerBankAccount);
	
	public Mono<CustomerBankAccount> saveBusinessAccount(CustomerBankAccountDto businessBankAccount);

	Mono<CustomerBankAccount> get(String customerAccountId);

	Flux<CustomerBankAccount> getAll();

	Mono<CustomerBankAccount> update(CustomerBankAccount customerBankAccount, String customerAccountId);

	Mono<Void> delete(String customerAccountId);
}
