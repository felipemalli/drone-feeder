package io.github.nataelienai.dronefeeder.video.exception;

/**
 * Thrown when video is already linked with a delivery.
 */
public class VideoAlreadyLinkedException extends RuntimeException {

  public VideoAlreadyLinkedException(String message) {
    super(message);
  }

}
