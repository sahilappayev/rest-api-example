package com.example.rest.error;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(Exception ex, HttpServletRequest request) {
        log.error(ex.getMessage(), ex);
        return ErrorResponse.builder().timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .errors(List.of("Internal server error"))
                .path(request.getServletPath())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CustomNotFoundException.class)
    public ErrorResponse handleCustomNotFoundException(CustomNotFoundException ex, HttpServletRequest request) {
        log.error(ex.getMessage(), ex);
        return ErrorResponse.builder().timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND)
                .errors(List.of(ex.getMessage()))
                .path(request.getServletPath())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                               HttpServletRequest request) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(fieldError -> fieldError.getField() + " - " + fieldError.getDefaultMessage()).toList();

        log.error(ex.getMessage(), ex);
        return ErrorResponse.builder().timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND)
                .errors(errors)
                .path(request.getServletPath())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ConstraintViolationException.class})
    public ErrorResponse handleHandlerMethodValidationException(ConstraintViolationException ex,
                                                                HttpServletRequest request) {
        List<String> errors = ex.getConstraintViolations().stream()
                .map(validation -> {
                    // create.name ->  [create, name]
                    String[] split = validation.getPropertyPath().toString().split("[.]");
                    return split[split.length - 1] + " - " + validation.getMessage();
                }).toList();

        log.error(ex.getMessage(), ex);
        return ErrorResponse.builder().timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND)
                .errors(errors)
                .path(request.getServletPath())
                .build();
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleAuthorizationDeniedException(AuthorizationDeniedException ex,
                                                            HttpServletRequest request) {
        log.error(ex.getMessage(), ex);
        return ErrorResponse.builder().timestamp(LocalDateTime.now())
                .status(HttpStatus.FORBIDDEN)
                .errors(List.of(ex.getMessage()))
                .path(request.getServletPath())
                .build();
    }

}
