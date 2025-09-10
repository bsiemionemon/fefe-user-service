package com.fefe.user_service.service;

import com.fefe.user_service.event.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String USER_REGISTERED_TOPIC = "user-registered";

    public void publishUserRegistered(UserRegisteredEvent event) {
        log.info("Sending user registered event to Kafka: {}", event);

        CompletableFuture<SendResult<String, Object>> future =
                kafkaTemplate.send(USER_REGISTERED_TOPIC, event.getUserId().toString(), event);

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Failed to send message to Kafka: {}", ex.getMessage(), ex);
            } else {
                log.info("Message sent successfully to topic {} at offset {}",
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().offset());
            }
        });
    }
}

