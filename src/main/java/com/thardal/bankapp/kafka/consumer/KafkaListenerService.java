package com.thardal.bankapp.kafka.consumer;

import com.thardal.bankapp.kafka.dto.KafkaMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaListenerService {

    @KafkaListener(
            topics = "${bankapp.kafka.topic}",
            groupId = "${bankapp.kafka.group-id})"
    )
    public void listen(@Payload KafkaMessage message) {
        log.info("Received Messasge in group - group-id: " + message.getMessage());
    }
}
