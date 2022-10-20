package io.github.felipemalli.dronefeeder.video.exception;

/**
 * Thrown when a video entity is not found.
 */
public class VideoNotFoundException extends RuntimeException {

  public VideoNotFoundException(String message) {
    super(message);
  }

}
