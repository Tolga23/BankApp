package com.thardal.bankapp.kafka.producer;

import com.thardal.bankapp.kafka.dto.KafkaMessage;
import com.thardal.bankapp.log.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
@RequiredArgsConstructor
public class KafkaMessageController {
    private final LogService logService;

    @PostMapping
    public void sendMessage(@RequestBody KafkaMessage logMessage) {

        System.out.println("Starting to send message to Kafka");

            logService.log(logMessage);

    }
}
