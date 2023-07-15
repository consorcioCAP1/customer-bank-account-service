package com.nttdata.bootcamp.customerbankaccountservice.service;

import com.nttdata.bootcamp.customerbankaccountservice.documents.CustomerBankAccount;
import com.nttdata.bootcamp.customerbankaccountservice.dto.CustomerBankAccountDto;
import reactor.core.publisher.Mono;

public interface CustomerBankAccountService {

	public Mono<CustomerBankAccount> saveCustomerAccount(CustomerBankAccountDto customerBankAccount);
	
	public Mono<CustomerBankAccount> saveBusinessAccount(CustomerBankAccountDto businessBankAccount);

}
