package io.github.felipemalli.dronefeeder.delivery;

import io.github.felipemalli.dronefeeder.delivery.exception.InvalidDeliveryStatusCodeException;

/**
 * Valid status for deliveries.
 */
public enum Status {

  READY(1), SHIPPED(2), DELIVERED(3);

  private final int code;

  private Status(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }

  /**
   * Retrieves a status by its code.
   *
   * @param code the code of the status to return.
   * @return the status with the given code.
   * @throws InvalidDeliveryStatusCodeException if {@literal code} does not exist.
   */
  public static Status valueOf(int code) {
    for (Status value : Status.values()) {
      if (value.getCode() == code) {
        return value;
      }
    }
    throw new InvalidDeliveryStatusCodeException(
        "Invalid status code: " + code
    );
  }
}
