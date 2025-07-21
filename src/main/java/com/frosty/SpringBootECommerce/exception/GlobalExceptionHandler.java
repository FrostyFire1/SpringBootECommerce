package com.frosty.SpringBootECommerce.exception;

import com.frosty.SpringBootECommerce.payload.APIResponse;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> invalidMethodArgumentHandler(
      MethodArgumentNotValidException e) {
    Map<String, String> response = new HashMap<>();
    e.getBindingResult()
        .getAllErrors()
        .forEach(
            (error) -> {
              FieldError err = (FieldError) error;
              response.put(err.getField(), err.getDefaultMessage());
            });
    return ResponseEntity.badRequest().body(response);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<APIResponse> resourceNotFoundHandler(ResourceNotFoundException e) {
    return new ResponseEntity<>(new APIResponse(e.getMessage(), false), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(APIException.class)
  public ResponseEntity<APIResponse> APIHandler(APIException e) {
    return ResponseEntity.badRequest().body(new APIResponse(e.getMessage(), false));
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<APIResponse> missingServletRequestParameterHandler(
      MissingServletRequestParameterException e) {
    return ResponseEntity.badRequest().body(new APIResponse(e.getMessage(), false));
  }
}
