package com.example.rest.error;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Builder
@Getter
@Setter
@ToString
public class ErrorResponse {

    private LocalDateTime timestamp;
    private HttpStatus status;
    private List<String> errors;
    private String path;

}
