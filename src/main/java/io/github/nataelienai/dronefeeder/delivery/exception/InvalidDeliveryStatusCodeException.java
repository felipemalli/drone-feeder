package io.github.nataelienai.dronefeeder.delivery.exception;

/**
 * Thrown when a delivery status code is invalid.
 */
public class InvalidDeliveryStatusCodeException extends RuntimeException {

  public InvalidDeliveryStatusCodeException(String message) {
    super(message);
  }

}
