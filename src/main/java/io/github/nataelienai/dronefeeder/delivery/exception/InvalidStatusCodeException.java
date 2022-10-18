package io.github.nataelienai.dronefeeder.delivery.exception;

/**
 * Thrown when a delivery status code is invalid.
 */
public class InvalidStatusCodeException extends RuntimeException {

  public InvalidStatusCodeException(String message) {
    super(message);
  }

}
