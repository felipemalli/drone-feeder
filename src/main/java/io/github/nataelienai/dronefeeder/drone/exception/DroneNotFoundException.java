package io.github.nataelienai.dronefeeder.drone.exception;

/**
 * Thrown when a drone entity is not found.
 */
public class DroneNotFoundException extends RuntimeException {

  public DroneNotFoundException(String message) {
    super(message);
  }

}
