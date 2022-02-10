package com.yoti.test.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoti.test.database.entity.CleaningConditionEntity;
import com.yoti.test.database.entity.CleaningResultEntity;
import com.yoti.test.exception.MapperException;
import com.yoti.test.model.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CleaningDataAccessMapper {
    private final ObjectMapper jsonMapper = new ObjectMapper();

    public CleaningConditionEntity toEntity(RequestCleaning requestCleaning) {
        try {
            String json = jsonMapper.writeValueAsString(requestCleaning);

            return new CleaningConditionEntity()
                .setData(json)
                .setCreatedAt(LocalDateTime.now());
        } catch (Exception ex) {
            throw new MapperException("Mapping error", ex);
        }
    }

    public CleaningResultEntity toEntity(ResponseCleaning responseCleaning) {
        try {
            String json = jsonMapper.writeValueAsString(responseCleaning);

            return new CleaningResultEntity()
                .setData(json)
                .setCreatedAt(LocalDateTime.now());
        } catch (Exception ex) {
            throw new MapperException("Mapping error", ex);
        }
    }

    public CommonResponseCleaning toCommonResponse(CleaningConditionEntity entity) {
        return CommonResponseCleaning.builder()
            .request(entity.getData())
            .result(entity.getCleaningResult().getData())
            .requestTime(entity.getCreatedAt())
            .resultTime(entity.getCleaningResult().getCreatedAt())
            .build();
    }
}
