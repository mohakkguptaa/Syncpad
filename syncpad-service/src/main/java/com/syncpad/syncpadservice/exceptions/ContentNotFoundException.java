package com.syncpad.syncpadservice.exceptions;

public class ContentNotFoundException extends RuntimeException {
  public ContentNotFoundException(String message) {
    super(message);
  }
}
