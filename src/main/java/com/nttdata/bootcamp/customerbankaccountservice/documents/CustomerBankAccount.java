package com.nttdata.bootcamp.customerbankaccountservice.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.Builder;
import lombok.Data;

@Document(collection = "customerBankAccount")
@Data
@Builder
public class CustomerBankAccount {

	@Id
	private String id;
	private String typeCustomer;
	private String clientId;
	private String name;
	private String dni;
	private String businessName;
	private String ruc;
	private Double maintenanceFeeApplies;
	private int bankMovementLimit;
	private int bankMovementDay;
	private String[] bankAccountHolder;
	private String[] bankSignatory;
	@Field("accountBalance")
	private Double accountBalance;
	private String openingDate;
	private String bankAccountNumber;
	private String accountType;
}
