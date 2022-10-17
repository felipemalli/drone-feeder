package io.github.nataelienai.dronefeeder.delivery;

import io.github.nataelienai.dronefeeder.delivery.exception.InvalidStatusValueException;
import java.util.LinkedList;
import java.util.List;

public enum Status {

  READY(1), SHIPPED(2), DELIVERED(3);

  private int code;

  private Status(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }

  private static List<String> getStatusNames() {
    List<String> statusNames = new LinkedList<>();
    for (Status value : Status.values()) {
      statusNames.add(value.name());
    }
    return statusNames;
  }

  public static Status valueOf(int code) {
    for (Status value : Status.values()) {
      if (value.getCode() == code) {
        return value;
      }
    }
    throw new InvalidStatusValueException(
      "Invalid status value, valid values are: " + getStatusNames()
    );
  }
}
