package com.thardal.bankapp.kafka.dto;

import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KafkaMessage {
    private Long id;
    private String message;
    private String description;
    private Date dateTime;
}
