package com.nttdata.bootcamp.customerbankaccountservice.service;

import com.nttdata.bootcamp.customerbankaccountservice.documents.CustomerBankAccount;
import com.nttdata.bootcamp.customerbankaccountservice.dto.CustomerBankAccountDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerBankAccountService {

	public Mono<CustomerBankAccount> saveCustomerAccount(CustomerBankAccountDto customerBankAccount);
	public Mono<CustomerBankAccount> saveBusinessAccount(CustomerBankAccountDto businessBankAccount);
	public Mono<Double> getAccountBalanceByBankAccountNumber(String bankAccountNumber);
	public Mono<CustomerBankAccount> updateAccountBalance(String bankAccountNumber, Double accountBalance);
	public Mono<CustomerBankAccount> getCustomerBankAccountByAccountNumber(String bankAccountNumber);
	public Flux<CustomerBankAccount> findByRucAndTypeAccount(String ruc, String typeAccount);
}
