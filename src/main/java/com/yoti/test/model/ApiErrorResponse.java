package com.yoti.test.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Value
public class ApiErrorResponse {
    String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    LocalDateTime timestamp;

    private List<ApiErrorDetail> errorDetails;

    public ApiErrorResponse(String description) {
        this.description = description;
        this.timestamp = LocalDateTime.now();
        this.errorDetails = new ArrayList<>();
    }

    public ApiErrorResponse(String description, List<ApiErrorDetail> details) {
        this.description = description;
        this.timestamp = LocalDateTime.now();
        this.errorDetails = details;
    }
}
