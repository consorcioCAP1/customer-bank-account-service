package com.nttdata.bootcamp.customerbankaccountservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nttdata.bootcamp.customerbankaccountservice.dto.CustomerBankAccountDto;
import com.nttdata.bootcamp.customerbankaccountservice.service.CustomerBankAccountService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
public class CustomerBankAccountController {

	@Autowired
	private CustomerBankAccountService customerBankAccountService;

	@PostMapping("/createCustomerPersonalAccount")
    public   Mono<ResponseEntity<Object>> createCustomerPersonalAccount(@RequestBody CustomerBankAccountDto customerDto) {
		
		return customerBankAccountService.saveCustomerAccount(customerDto)
				 .flatMap(objResponse -> {
	                    ResponseEntity<Object> responseEntity = ResponseEntity.ok(objResponse);
	                    return Mono.just(responseEntity);
	                })
	                .onErrorResume(error -> {
	                    ResponseEntity<Object> responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error.getMessage());
	                    return Mono.just(responseEntity);
	                });
	}

	@PostMapping("/createCustomerBusinessAccount")
    public   Mono<ResponseEntity<Object>> createCustomerBusinessAccount(@RequestBody CustomerBankAccountDto customerDto) {

		return customerBankAccountService.saveBusinessAccount(customerDto)
				 .flatMap(objResponse -> {
	                    ResponseEntity<Object> responseEntity = ResponseEntity.ok(objResponse);
	                    return Mono.just(responseEntity);
	                })
	                .onErrorResume(error -> {
	                    ResponseEntity<Object> responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error.getMessage());
	                    return Mono.just(responseEntity);
	                });
	}
}
