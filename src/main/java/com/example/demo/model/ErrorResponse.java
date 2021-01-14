package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    @JsonProperty("error")
    private String error;

    @JsonProperty("error_status")
    private int errorStatus;

    @JsonProperty("error_description")
    private String errorDescription;

    public static ErrorResponse serverError(String message) {
        return builder()
                .error("server_error")
                .errorDescription(message)
                .errorStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
    }

    public static ErrorResponse serverError() {
        return serverError("System error");
    }
}
