package com.allianceever.projectERP.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@JsonInclude(NON_NULL)
@SuperBuilder
public class Response {
    private LocalDateTime timeStamp;
    private HttpStatus status;
    private Integer statusCode;
    private String message;
    private Map<?, ?> data;
    private String devMessage;
}
