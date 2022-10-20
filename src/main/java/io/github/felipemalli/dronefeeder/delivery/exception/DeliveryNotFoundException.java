package io.github.felipemalli.dronefeeder.delivery.exception;

/**
 * Thrown when a delivery entity is not found.
 */
public class DeliveryNotFoundException extends RuntimeException {
  
  public DeliveryNotFoundException(String message) {
    super(message);
  }

}
