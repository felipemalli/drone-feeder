package io.github.nataelienai.dronefeeder.delivery.exception;

/**
 * Thrown when an entity is not found.
 */
public class DeliveryNotFoundException extends RuntimeException {
  
  public DeliveryNotFoundException(String message) {
    super(message);
  }

}
