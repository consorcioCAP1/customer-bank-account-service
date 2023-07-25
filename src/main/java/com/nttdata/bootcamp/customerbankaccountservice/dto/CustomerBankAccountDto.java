package com.nttdata.bootcamp.customerbankaccountservice.dto;

import lombok.Data;

@Data
public class CustomerBankAccountDto {
	private String id;
	private String typeCustomer;
	private String clientId;
	private String clientName;
	private String numberDocument;
	private Double maintenanceFeeApplies;
	private int bankMovementLimit;
	private int bankMovementDay;
	private String[] bankAccountHolder;
	private String[] bankSignatory;
	private Double accountBalance;
	private String bankAccountNumber;
	private String accountType;
	private double minimumOpeningAmount;
}
