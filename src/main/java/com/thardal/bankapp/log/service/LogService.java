package com.thardal.bankapp.log.service;

import com.thardal.bankapp.kafka.dto.KafkaMessage;
import com.thardal.bankapp.log.service.entityservice.LogDetailsEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LogService {

    @Value("${bankapp.kafka.topic}")
    private String topic;

    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;

    public void log(KafkaMessage logMessage) {
        String id = UUID.randomUUID().toString();

        kafkaTemplate.send(topic, id, logMessage);
    }
}
