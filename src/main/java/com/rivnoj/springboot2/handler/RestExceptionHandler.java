package com.rivnoj.springboot2.handler;

import org.springframework.http.HttpHeaders;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.rivnoj.springboot2.exception.BadRequestException;
import com.rivnoj.springboot2.exception.BadRequestExceptionDetails;
import com.rivnoj.springboot2.exception.ExceptionDetails;
import com.rivnoj.springboot2.exception.ValidationExceptionDetails;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<BadRequestExceptionDetails> handleBadRequestExcepption(
      BadRequestException badRequestException) {
    return new ResponseEntity<>(
        BadRequestExceptionDetails.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .title("Bad request exception, check the documentation")
            .details(badRequestException.getMessage())
            .devoloperMessage(badRequestException.getClass().getName())
            .build(),
        HttpStatus.BAD_REQUEST);
  }
  
  @Override
  @SuppressWarnings("null")
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
    String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
    String fieldsMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));

    return new ResponseEntity<>(
        ValidationExceptionDetails.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .title("Bad request exception, invalid fields")
            .details("Check the field(s) error")
            .devoloperMessage(ex.getClass().getName())
            .fields(fields)
            .fieldsMessage(fieldsMessage)
            .build(),
        HttpStatus.BAD_REQUEST);
  }

  @Override
  @SuppressWarnings("null")
  protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers,
      HttpStatus statusCode, WebRequest request) {

    ExceptionDetails exDetails = ExceptionDetails.builder()
        .timestamp(LocalDateTime.now())
        .status(statusCode.value())
        .title(ex.getCause().getMessage())
        .details(ex.getMessage())
        .devoloperMessage(ex.getClass().getName())
        .build();

    return new ResponseEntity<>(exDetails, headers, statusCode);
  }
}
