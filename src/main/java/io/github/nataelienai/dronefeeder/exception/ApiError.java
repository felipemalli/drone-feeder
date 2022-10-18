package io.github.nataelienai.dronefeeder.exception;

/**
 * Response entity for any exception that occurred during the API runtime.
 */
public class ApiError {

  private String message;

  public ApiError(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

}
