package com.frosty.SpringBootECommerce.exception;

public class ResourceNotFoundException extends RuntimeException {
  private String resource;
  private String field;
  private String fieldName;
  private Long fieldId;

  public ResourceNotFoundException(String resource, String field, String fieldName) {
    super(String.format("%s not found with %s: %s", resource, field, fieldName));
    this.resource = resource;
    this.field = field;
    this.fieldName = fieldName;
  }

  public ResourceNotFoundException(String resource, String field, Long fieldId) {
    super(String.format("%s not found with %s: %d", resource, field, fieldId));
    this.resource = resource;
    this.field = field;
    this.fieldId = fieldId;
  }
}
