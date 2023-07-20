package com.nttdata.bootcamp.customerbankaccountservice.utilities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.nttdata.bootcamp.customerbankaccountservice.documents.CustomerBankAccount;
import com.nttdata.bootcamp.customerbankaccountservice.dto.CustomerBankAccountDto;

public class BuildCustomers {
	
	private BuildCustomers() {}
	//construccion de la cuenta de ahorro
	public static CustomerBankAccount buildCustomerSavingsCurrentAccount(CustomerBankAccountDto dto) {
		LocalDateTime currentDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return CustomerBankAccount.builder()
				.name(dto.getName())
				.dni(dto.getDni())
				.bankMovementLimit(dto.getBankMovementLimit())
				.typeCustomer(dto.getTypeCustomer())
				.accountType(dto.getAccountType())
				.accountBalance(dto.getAccountBalance())
				.openingDate(currentDate.format(formatter))
				.bankAccountNumber(dto.getBankAccountNumber()).build();
	}

	//construccion de la cuenta corriente
	public static CustomerBankAccount buildCustomerCurrentAccount(CustomerBankAccountDto dto) {
		LocalDateTime currentDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return CustomerBankAccount.builder()
				.name(dto.getName())
				.dni(dto.getDni())
				.maintenanceFeeApplies(dto.getMaintenanceFeeApplies())
				.typeCustomer(dto.getTypeCustomer())
				.accountBalance(dto.getAccountBalance())
				.openingDate(currentDate.format(formatter))
				.bankAccountNumber(dto.getBankAccountNumber()).build();
	}

	//construccion de la cuenta plazo fijo
	public static CustomerBankAccount buildCustomerFixedTermAccount(CustomerBankAccountDto dto) {
		LocalDateTime currentDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return CustomerBankAccount.builder()
				.name(dto.getName())
				.dni(dto.getDni())
				.typeCustomer(dto.getTypeCustomer())
				.bankMovementDay(dto.getBankMovementDay())
				.accountBalance(dto.getAccountBalance())
				.openingDate(currentDate.format(formatter))
				.bankAccountNumber(dto.getBankAccountNumber()).build();
	}

	//construccion de la cuenta corriente empresarial
	public static CustomerBankAccount buildBusinessCurrentAccount(CustomerBankAccountDto dto) {
		LocalDateTime currentDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return CustomerBankAccount.builder()
				.businessName(dto.getBusinessName())
				.ruc(dto.getRuc())
				.maintenanceFeeApplies(dto.getMaintenanceFeeApplies())
				.typeCustomer(dto.getTypeCustomer())
				.accountBalance(dto.getAccountBalance())
				.openingDate(currentDate.format(formatter))
				.bankAccountNumber(dto.getBankAccountNumber()).build();
	}


}
