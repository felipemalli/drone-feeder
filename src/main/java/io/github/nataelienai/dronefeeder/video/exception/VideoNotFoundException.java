package io.github.nataelienai.dronefeeder.video.exception;

/**
 * Thrown when a video entity is not found.
 */
public class VideoNotFoundException extends RuntimeException {

  public VideoNotFoundException(String message) {
    super(message);
  }

}
