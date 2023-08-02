package com.thardal.bankapp.kafka.consumer;

import com.thardal.bankapp.kafka.dto.KafkaMessage;
import com.thardal.bankapp.log.converter.LogMapper;
import com.thardal.bankapp.log.entity.LogDetails;
import com.thardal.bankapp.log.service.entityservice.LogDetailsEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaListenerService {
    private final LogDetailsEntityService logDetailsEntityService;

    @KafkaListener(
            topics = "${bankapp.kafka.topic}",
            groupId = "${bankapp.kafka.group-id})"
    )
    public void listen(@Payload KafkaMessage message) {

        log.info("Received Messasge in group - group-id: " + message.getMessage());

        saveLogToDb(message);
    }

    @Transactional
    public void saveLogToDb(KafkaMessage message) {
        LogDetails logDetails = LogMapper.INSTANCE.convertToLogDetails(message);


        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logDetails = logDetailsEntityService.save(logDetails);
        System.out.println("****************** LogDetails saved *************************");
    }
}
