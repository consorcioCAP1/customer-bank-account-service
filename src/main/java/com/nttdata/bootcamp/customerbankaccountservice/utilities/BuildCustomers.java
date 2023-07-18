package com.nttdata.bootcamp.customerbankaccountservice.utilities;

import com.nttdata.bootcamp.customerbankaccountservice.documents.CustomerBankAccount;
import com.nttdata.bootcamp.customerbankaccountservice.dto.CustomerBankAccountDto;

public class BuildCustomers {
	
	private BuildCustomers() {}
	//construccion de la cuenta de ahorro
	public static CustomerBankAccount buildCustomerSavingsAccount(CustomerBankAccountDto dto) {
		return CustomerBankAccount.builder()
				.name(dto.getName())
				.dni(dto.getDni())
				.typeCustomer(dto.getTypeCustomer())
				.maintenanceFeeApplies(0.00)
				.bankMovementLimit(dto.getBankMovementLimit())
				.accountBalance(dto.getAccountBalance())
				.openingDate(dto.getOpeningDate())
				.bankAccountNumber(dto.getBankAccountNumber()).build();
	}

	//construccion de la cuenta corriente
	public static CustomerBankAccount buildCustomerCurrentAccount(CustomerBankAccountDto dto) {
		return CustomerBankAccount.builder()
				.name(dto.getName())
				.dni(dto.getDni())
				.typeCustomer(dto.getTypeCustomer())
				.maintenanceFeeApplies(dto.getMaintenanceFeeApplies())
				.bankMovementLimit(0)
				.bankMovementDay(dto.getBankMovementDay())
				.accountBalance(dto.getAccountBalance())
				.openingDate(dto.getOpeningDate())
				.bankAccountNumber(dto.getBankAccountNumber()).build();
	}

	//construccion de la cuenta plazo fijo
	public static CustomerBankAccount buildCustomerFixedTermAccount(CustomerBankAccountDto dto) {
		return CustomerBankAccount.builder()
				.name(dto.getName())
				.dni(dto.getDni())
				.clientId(dto.getClientId())
				.typeCustomer(dto.getTypeCustomer())
				.maintenanceFeeApplies(0.00)
				.bankMovementLimit(0)
				.bankMovementDay(dto.getBankMovementDay())
				.accountBalance(dto.getAccountBalance())
				.openingDate(dto.getOpeningDate())
				.bankAccountNumber(dto.getBankAccountNumber()).build();
	}

	//construccion de la cuenta plazo fijo
	public static CustomerBankAccount buildCustomerBusiness(CustomerBankAccountDto dto) {
		return CustomerBankAccount.builder()
				.ruc(dto.getRuc())
				.businessName(dto.getBusinessName())
				.clientId(dto.getClientId())
				.typeCustomer(dto.getTypeCustomer())
				.maintenanceFeeApplies(0.00)
				.bankMovementLimit(0)
				.bankMovementDay(dto.getBankMovementDay())
				.accountBalance(dto.getAccountBalance())
				.openingDate(dto.getOpeningDate())
				.bankAccountNumber(dto.getBankAccountNumber()).build();
	}
}
