package com.nttdata.bootcamp.customerbankaccountservice.service.impl;


import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.bootcamp.customerbankaccountservice.dto.TransactionDto;
import com.nttdata.bootcamp.customerbankaccountservice.repository.CustomerBankAccountRepository;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

@Slf4j
@Service
public class KakfaService {

	@Autowired
	CustomerBankAccountRepository repository;
	
	private ObjectMapper objectMapper;
	@Autowired
	private KafkaReceiver<String, String> kafkaReceiver;
	@Autowired
	private KafkaReceiver<String, TransactionDto> kafkaReceiverTransaction;
	
	@Autowired
	private KafkaSender<String, String> kafkaSender;
    private final String responseTopic = "topicUpdateWallet2";
    private final String topicUpdateBalanceWallet = "transactionBootCoin";
    
    @PostConstruct
    public void startConsumeTopic() {
    	consumeTopics();
    }

    private void consumeTopics() {
        kafkaReceiver.receive()
            .doOnNext(record -> {
                String topic = record.topic();
                String value = record.value();

                if ("topicUpdateWallet".equals(topic)) {
                	consumeTopicUpdateWallet(value);
                } else if ("updateBalanceAccount".equals(topic)) {
                	updateBalanceAccount(value);
                } else {
                    log.warn("others topic: ", topic);
                }
            })
            .subscribe();
        
        kafkaReceiverTransaction.receive()
        .doOnNext(record -> {
            String topic = record.topic();
            TransactionDto value = record.value();

            if (topicUpdateBalanceWallet.equals(topic)) {
            	consumeTopicTransactionBootCoin(value);
            } else {
                log.warn("others topic: ", topic);
            }
        })
        .subscribe();
        
    }
    
	public void consumeTopicTransactionBootCoin(TransactionDto value){
		 
		System.out.println("Transaction received: " + value);
		 
	}

	public void consumeTopicUpdateWallet(String value) {
	    try {
            JsonNode jsonNode = objectMapper.readTree(value);
            String numberCardDebit = jsonNode.get("numberCardDebit").asText();
            String phone = jsonNode.get("phone").asText();
            String primaryAccount = jsonNode.get("primaryAccount").asText();
            repository.findByBankAccountNumber(primaryAccount)
                .flatMap(accountBank -> {
                    // cargando info para nuevo topic
                    String message = "{\"numberCardDebit\": \"" + numberCardDebit 
                    	+ "\", \"phone\": \"" + phone
                    	+ "\", \"accountBalance\": \"" + accountBank.getAccountBalance()
                        + "\", \"primaryAccount\": \"" + primaryAccount + "\"}";
                    
                    return kafkaSender.send(Mono.just(SenderRecord.create(
                    			new ProducerRecord<>(responseTopic, message), null)))
                        .then();
                })
                .doOnError(error -> {
                    // Lógica para manejar errores "aun no se sabe como realizar esto"
                })
                .subscribe();
        } catch (Exception e) {
            e.printStackTrace();
            }
	}

	public void updateBalanceAccount(String value) {
	    try {
        	System.out.println("se esta pintando esta linea:"+value);
            JsonNode jsonNode = objectMapper.readTree(value);
            String bankAccountNumber = jsonNode.get("bankAccountNumber").asText();
            String accountBalance = jsonNode.get("accountBalance").asText();
            System.out.println(value);
            repository.findByBankAccountNumber(bankAccountNumber)
                .flatMap(accountBank -> {
                    accountBank.setAccountBalance(Double.parseDouble(accountBalance));
                    return repository.save(accountBank);
                })
                .doOnError(error -> {
                    // Lógica para manejar errores "aun no se sabe como realizar esto"
                })
                .subscribe();
        } catch (Exception e) {
            e.printStackTrace();
            }
	}

}
