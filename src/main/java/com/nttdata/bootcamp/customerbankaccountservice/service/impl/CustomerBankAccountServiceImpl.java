package com.nttdata.bootcamp.customerbankaccountservice.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.bootcamp.customerbankaccountservice.documents.CustomerBankAccount;
import com.nttdata.bootcamp.customerbankaccountservice.dto.CustomerBankAccountDto;
import com.nttdata.bootcamp.customerbankaccountservice.repository.CustomerBankAccountRepository;
import com.nttdata.bootcamp.customerbankaccountservice.service.CustomerBankAccountService;
import lombok.extern.slf4j.Slf4j;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class CustomerBankAccountServiceImpl implements CustomerBankAccountService{
	
	public final String TYPE_CUSTOMER_CUSTOMER = "CUSTOMER";
	public final String TYPE_CUSTOMER_BUSINESS = "BUSINESS";
	
	public final String ACCOUNT_TYPE_SAVING = "SAVING";
	public final String ACCOUNT_TYPE_CURRENT = "CURRENT";
	public final String ACCOUNT_TYPE_FIXED_TERM = "FIXED";

	@Autowired
	CustomerBankAccountRepository repository;
	

	//metodo para guardar cuentas clientes personal
	@Override
	public Mono<CustomerBankAccount> saveCustomerAccount(CustomerBankAccountDto customerBankAccount) {
		customerBankAccount.setTypeCustomer(TYPE_CUSTOMER_CUSTOMER);
		//si es cuenta de ahorro o corriente se valida la que no haya una previamente creada
		if (customerBankAccount.getAccountType().equals(ACCOUNT_TYPE_SAVING) || customerBankAccount.getAccountType().equals(ACCOUNT_TYPE_CURRENT) ) {
			//se realiza la busqueda de cuenta personal por dni y por el tipo de cuenta
			return repository.findByDniAndAccountType(customerBankAccount.getDni(), customerBankAccount.getAccountType())
				.hasElement()
	            .flatMap(hasElement -> {
	                if (hasElement) {
	                	log.info("El customer: "+customerBankAccount.getDni() +" ya tiene cuenta ahorro creado.");
	                	return Mono.error(new RuntimeException("El customer ya tiene cuenta ahorro creado."));
	                } else {
	                	log.info("registrando cuenta del cliente: "+ customerBankAccount.getDni());
	                	//contruyendo el document CustomerBankAccount
	                	CustomerBankAccount customerBankAccountDocument = CustomerBankAccount.builder()
	            				.name(customerBankAccount.getName())
	            				.dni(customerBankAccount.getDni())
	            				.maintenanceFeeApplies(0.00)
	            				.bankMovementLimit(customerBankAccount.getBankMovementLimit())
	            				.typeCustomer(customerBankAccount.getTypeCustomer())
	            				.accountType(customerBankAccount.getAccountType())
	            				.accountBalance(customerBankAccount.getAccountBalance())
	            				.openingDate(customerBankAccount.getOpeningDate())
	            				.bankAccountNumber(customerBankAccount.getBankAccountNumber()).build();
	                	return repository.save(customerBankAccountDocument);
	                }
	            });
		}
		//si es plazo fijo solo se registra
		else {
			log.info("registrando cuenta del cliente: "+ customerBankAccount.getDni());
			CustomerBankAccount customerBankAccountDocument = CustomerBankAccount.builder()
    				.name(customerBankAccount.getName())
    				.dni(customerBankAccount.getDni())
    				.maintenanceFeeApplies(0.00)
    				.typeCustomer(customerBankAccount.getTypeCustomer())
    				.accountType(customerBankAccount.getAccountType())
    				.bankMovementLimit(customerBankAccount.getBankMovementLimit())
    				.accountBalance(customerBankAccount.getAccountBalance())
    				.openingDate(customerBankAccount.getOpeningDate())
    				.bankAccountNumber(customerBankAccount.getBankAccountNumber()).build();
			return repository.save(customerBankAccountDocument); 
		}
	}

	@Override
	public Mono<CustomerBankAccount> saveBusinessAccount(CustomerBankAccountDto businessBankAccount) {
		if (businessBankAccount.getAccountType().equals(ACCOUNT_TYPE_CURRENT)) {
			log.info("registrando cuenta de empresarial de cliente: "+ businessBankAccount.getDni());
			CustomerBankAccount customerBankAccountDocument = CustomerBankAccount.builder()
					.ruc(businessBankAccount.getRuc())
					.businessName(businessBankAccount.getBusinessName())
					.clientId(businessBankAccount.getClientId())
					.typeCustomer(businessBankAccount.getTypeCustomer())
					.maintenanceFeeApplies(0.00)
					.bankMovementLimit(0)
					.bankMovementDay(businessBankAccount.getBankMovementDay())
					.accountBalance(businessBankAccount.getAccountBalance())
					.openingDate(businessBankAccount.getOpeningDate())
					.bankAccountNumber(businessBankAccount.getBankAccountNumber()).build();
			return repository.save(customerBankAccountDocument);
		}
		else return Mono.error(new RuntimeException("El Cliente Empresarial solo puede tener cuentas corrientes.")); 
	}
	
	@Override
	public Mono<CustomerBankAccount> get(String customerAccountId) {

		return null;
	}

	@Override
	public Flux<CustomerBankAccount> getAll() {

		return null;
	}

	@Override
	public Mono<CustomerBankAccount> update(CustomerBankAccount customerBankAccount, String customerAccountId) {

		return null;
	}

	@Override
	public Mono<Void> delete(String customerAccountId) {

		return null;
	}



	
}
