package io.github.nataelienai.dronefeeder.delivery.exception;

/**
 * Thrown when a delivery status value is invalid.
 */
public class InvalidStatusValueException extends RuntimeException {

  public InvalidStatusValueException(String message) {
    super(message);
  }

}
