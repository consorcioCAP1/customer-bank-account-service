package com.nttdata.bootcamp.customerbankaccountservice.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.bootcamp.customerbankaccountservice.documents.CustomerBankAccount;
import com.nttdata.bootcamp.customerbankaccountservice.dto.CustomerBankAccountDto;
import com.nttdata.bootcamp.customerbankaccountservice.repository.CustomerBankAccountRepository;
import com.nttdata.bootcamp.customerbankaccountservice.service.CustomerBankAccountService;
import com.nttdata.bootcamp.customerbankaccountservice.utilities.BuildCustomers;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class CustomerBankAccountServiceImpl implements CustomerBankAccountService{
	
	public static final String TYPE_CUSTOMER_PERSONAL = "PERSONAL";
	public static final String TYPE_CUSTOMER_BUSINESS = "BUSINESS";
	
	public static final String ACCOUNT_TYPE_SAVING = "SAVING";
	public static final String ACCOUNT_TYPE_CURRENT = "CURRENT";
	public static final String ACCOUNT_TYPE_FIXED_TERM = "FIXED";

	@Autowired
	CustomerBankAccountRepository repository;
	

	//metodo para guardar cuentas clientes personal
	@Override
	public Mono<CustomerBankAccount> saveCustomerAccount(CustomerBankAccountDto customerBankAccount) {
		//validacion para monto minimo de apertura
		if(customerBankAccount.getMinimumOpeningAmount()>customerBankAccount.getAccountBalance())
			return Mono.error(new RuntimeException("Monto minimo de apertura no superado."));
		customerBankAccount.setTypeCustomer(TYPE_CUSTOMER_PERSONAL);
		if (customerBankAccount.getAccountType().equals(ACCOUNT_TYPE_SAVING) || 
				customerBankAccount.getAccountType().equals(ACCOUNT_TYPE_CURRENT) ) {
			//se valida que no exista cuenta creada
			 return validateSaveAndCurrentAccount(customerBankAccount);
		}
		//si es plazo fijo solo se registra
		else {
			log.info("registrando cuenta del cliente: "+ customerBankAccount.getNumberDocument());
			CustomerBankAccount customerBankAccountDocument = 
        			BuildCustomers.buildCustomerFixedTermAccount(customerBankAccount);
			return repository.save(customerBankAccountDocument); 
		}
	}

	private Mono<CustomerBankAccount>validateSaveAndCurrentAccount(CustomerBankAccountDto customerBankAccount){
	    return repository.findByNumberDocumentAndAccountType(customerBankAccount.getNumberDocument(),
	    			customerBankAccount.getAccountType())
	        .next()
	        .hasElement()
	        .flatMap(hasElement -> {
	            if (hasElement) {
	                log.info("Cliente: " + customerBankAccount.getNumberDocument() 
	                				+" ya posee cuenta creada.");
	                return Mono.error(new RuntimeException("El cliente ya tiene cuenta creada."));
	            } else {
	                log.info("registrando cuenta del cliente: " + customerBankAccount.getNumberDocument());
	                CustomerBankAccount customerBankAccountDocument;
	                if (customerBankAccount.getAccountType().equals(ACCOUNT_TYPE_SAVING)) {
	                    customerBankAccountDocument = 
	                    		BuildCustomers.buildCustomerSavingsCurrentAccount(customerBankAccount);
	                } else {
	                    customerBankAccountDocument = 
	                    		BuildCustomers.buildCustomerCurrentAccount(customerBankAccount);
	                }
	                return repository.save(customerBankAccountDocument);
	            }
	        });
	}

	@Override
	public Mono<CustomerBankAccount> saveBusinessAccount(CustomerBankAccountDto businessBankAccount) {
		if(businessBankAccount.getMinimumOpeningAmount()>businessBankAccount.getAccountBalance())
			return Mono.error(new RuntimeException("Monto minimo de apertura no superado."));
		//validando que cuenta debe tener al menos un titular
		if (businessBankAccount.getBankAccountHolder().length>0) {
			if (businessBankAccount.getAccountType().equals(ACCOUNT_TYPE_CURRENT)) {
				log.info("registrando cuenta empresarial: "+ businessBankAccount.getNumberDocument());
				CustomerBankAccount businessBankAccountDocument = 
	        			BuildCustomers.buildBusinessCurrentAccount(businessBankAccount);
				return repository.save(businessBankAccountDocument);
			}
			else return Mono.error(
				new RuntimeException("El Cliente Empresarial solo puede tener cuenta corriente."));
		}
		else return Mono.error(
			new RuntimeException("Las cuentas empresariales deben tener al menos 1 titular."));
		 
	}

	//metodo para obtener el saldo disponible del número de cuenta
	@Override
	public Mono<Double> getAccountBalanceByBankAccountNumber(String bankAccountNumber){
		return repository.findByBankAccountNumber(bankAccountNumber)
	            .map(bankAccount -> bankAccount.getAccountBalance());
    }
	
	//metodo para obtener el documento Customer by account por número de cuenta
	@Override
	public Mono<CustomerBankAccount> getCustomerBankAccountByAccountNumber(String bankAccountNumber){
		return repository.findByBankAccountNumber(bankAccountNumber);
    }

	//metodo para actualizar le account balance en base al número de cuenta
	@Override
	public Mono<CustomerBankAccount> updateAccountBalance(String bankAccountNumber, Double accountBalance){
	        return repository.findByBankAccountNumber(bankAccountNumber)
	                .flatMap(bankAccount -> {
	                	bankAccount.setAccountBalance(accountBalance);
	                    return repository.save(bankAccount);
	                });
	   }

	//metodo para obtener flux de bank account en pase al ruc y type
	@Override
	public Flux<CustomerBankAccount> findByRucAndTypeAccount(String ruc, String typeAccount){
		return repository.findByNumberDocumentAndAccountType(ruc, typeAccount);
	}	

	@Override
	public Flux<CustomerBankAccount> findByNumberDocument(String numberDocument){
		return repository.findByNumberDocument(numberDocument);
	}
}
