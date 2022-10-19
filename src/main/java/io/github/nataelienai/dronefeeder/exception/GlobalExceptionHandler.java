package io.github.nataelienai.dronefeeder.exception;

import io.github.nataelienai.dronefeeder.delivery.Status;
import io.github.nataelienai.dronefeeder.delivery.exception.DeliveryNotFoundException;
import io.github.nataelienai.dronefeeder.drone.exception.DroneNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handler for all exceptions thrown by the API.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({
      DeliveryNotFoundException.class,
      DroneNotFoundException.class
  })
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ApiError handleResourceNotFoundException(RuntimeException exception) {
    return new ApiError(exception.getMessage());
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiError handleHttpMessageNotReadableException(RuntimeException exception) {
    if (exception.getMessage().contains(Status.class.getName())) {
      return new ApiError("Invalid status name.");
    }
    return new ApiError(exception.getMessage());
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ApiError handleException(Exception exception) {
    return new ApiError("Internal Server Error.");
  }
}
