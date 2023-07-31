package com.thardal.bankapp.kafka.producer;

import com.thardal.bankapp.kafka.dto.KafkaMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/kafka")
@RequiredArgsConstructor
public class KafkaMessageController {
    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;
    @Value("${bankapp.kafka.topic}")
    private String topic;

    @PostMapping
    public void sendMessage(@RequestBody KafkaMessage kafkaMessage) {

        System.out.println("Starting to send message to Kafka");

        for (int i = 0; i < 6666; i++) {
            //String id = UUID.randomUUID().toString();
            String id = String.valueOf(i);
            kafkaMessage.setId(Long.valueOf(id));
            kafkaTemplate.send(topic, id, kafkaMessage);
        }

    }
}
