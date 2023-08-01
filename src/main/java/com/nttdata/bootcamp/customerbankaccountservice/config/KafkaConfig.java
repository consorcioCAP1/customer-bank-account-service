package com.nttdata.bootcamp.customerbankaccountservice.config;

import java.util.Arrays;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.bootcamp.customerbankaccountservice.dto.TransactionDto;
import com.nttdata.bootcamp.customerbankaccountservice.utilities.GsonDeserializer;

import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

@Configuration
public class KafkaConfig {

	@Bean
    public KafkaReceiver<String, String> stringKafkaReceiver() {
        ReceiverOptions<String, String> receiverOptions = ReceiverOptions.<String, String>create()
                .consumerProperty("bootstrap.servers", "localhost:29092")
                .consumerProperty("key.deserializer", StringDeserializer.class)
                .consumerProperty("value.deserializer", StringDeserializer.class)
                .consumerProperty("group.id", "myConsumerGroup")
                .subscription(Arrays.asList("topicUpdateWallet","updateBalanceAccount"));  
        return KafkaReceiver.create(receiverOptions);
    }

	@Bean
	public KafkaReceiver<String, TransactionDto> transactionDtoKafkaReceiver(ObjectMapper objectMapper) {
	    ReceiverOptions<String, TransactionDto> receiverOptions = ReceiverOptions.<String, TransactionDto>create()
	            .consumerProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092")
	            .consumerProperty("group.id", "myConsumerGroup")
                .subscription(Arrays.asList("transactionBootCoin"))
	            .withKeyDeserializer(new StringDeserializer())
	            .withValueDeserializer(new GsonDeserializer<>(TransactionDto.class));
	    return KafkaReceiver.create(receiverOptions);
	}
	
	@Bean
    public KafkaSender<String, String> kafkaSender() {
        SenderOptions<String, String> senderOptions = SenderOptions.<String, String>create()
                .producerProperty("bootstrap.servers", "localhost:29092")
                .producerProperty("key.serializer", StringSerializer.class)
                .producerProperty("value.serializer", StringSerializer.class);
        return KafkaSender.create(senderOptions);
    }

}