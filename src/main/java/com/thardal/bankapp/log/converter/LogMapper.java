package com.thardal.bankapp.log.converter;

import com.thardal.bankapp.kafka.dto.KafkaMessage;
import com.thardal.bankapp.log.entity.LogDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.core.log.LogMessage;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LogMapper {

    LogMapper INSTANCE = Mappers.getMapper(LogMapper.class);

    @Mapping(target = "details", source = "description")
    LogDetails convertToLogDetails(KafkaMessage kafkaMessage);
}
