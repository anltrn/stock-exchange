package com.stock.exchange.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final String HTTP_BODY_NOT_READABLE = "Http body not readable. Check if content of body is valid.";
    private static final String HTTP_INVALID_MEDIA_TYPE = "Media type is invalid";

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.info("Illegal argument exception occurred: {}", ex.getMessage());
        return new ResponseEntity<>(ErrorResponse.builder()
                .date(LocalDateTime.now())
                .message("Illegal Argument Error:")
                .details(List.of(ex.getMessage()))
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<?> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        log.info("Method not supported exception occurred: {}", ex.getMessage());
        return new ResponseEntity<>(ErrorResponse.builder()
                .date(LocalDateTime.now())
                .message("Method Not Supported:")
                .details(List.of(ex.getMessage()))
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex) {
        log.info("Entity not found exception occurred: {}", ex.getMessage());
        return new ResponseEntity<>(ErrorResponse.builder()
                .date(LocalDateTime.now())
                .message("Record Not Found")
                .details(List.of(ex.getMessage()))
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        log.info("Exception occurred: {}", ex.getMessage());
        return new ResponseEntity<>(ErrorResponse.builder()
                .date(LocalDateTime.now())
                .message("An unexpected error occurred: ")
                .details(List.of(ex.getMessage()))
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        log.info("Error on validation: {}", ex.getMessage());
        return new ResponseEntity<>(ErrorResponse.builder()
                .date(LocalDateTime.now())
                .message("Error on validation:")
                .details(ex.getBindingResult().getFieldErrors().stream()
                        .map(FieldError::getDefaultMessage)
                        .collect(Collectors.toList()))
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleInvalidBodyException(HttpMessageNotReadableException e) {
        log.info("HTTP body not readable: {}", e.getMessage());
        return new ResponseEntity<>(ErrorResponse.builder()
                .date(LocalDateTime.now())
                .message(HTTP_BODY_NOT_READABLE)
                .details(List.of(e.getMessage()))
                .build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<?> handleContentTypeException(HttpMediaTypeNotSupportedException e) {
        log.info("Invalid media type:", e.getMessage());
        return new ResponseEntity<>(ErrorResponse.builder()
                .date(LocalDateTime.now())
                .message(HTTP_INVALID_MEDIA_TYPE)
                .details(List.of(e.getMessage()))
                .build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex) {
        log.info("Constraint violation:", ex.getMessage());
        return new ResponseEntity<>(ErrorResponse.builder()
                .date(LocalDateTime.now())
                .message("Constraint Violation")
                .details(List.of(ex.getMessage()))
                .build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException e) {
        log.info("User Not Found: {}", e.getMessage());
        return new ResponseEntity<>(ErrorResponse.builder()
                .date(LocalDateTime.now())
                .message("UserName Not Found")
                .details(List.of(e.getMessage()))
                .build(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    public ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
        log.info("Parameter Missing:", ex.getMessage());
        List<String> details = new ArrayList<>();
        details.add(ex.getParameterName() + " parameter is missing");
        return new ResponseEntity<>(ErrorResponse.builder()
                .date(LocalDateTime.now())
                .message("Missing Parameters")
                .details(List.of(ex.getMessage()))
                .build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException ex) {
        log.info("CustomException occurred: {}", ex.getMessage());
        return new ResponseEntity<>(ErrorResponse.builder()
                .date(LocalDateTime.now())
                .message("CustomException occurred: ")
                .details(List.of(ex.getMessage()))
                .build(), ex.getHttpStatus());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public void handleAccessDeniedException(HttpServletResponse res) throws IOException {
        res.sendError(HttpStatus.FORBIDDEN.value(), "Access denied");
    }
}

