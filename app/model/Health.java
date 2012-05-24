package model;

import org.codehaus.jackson.annotate.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NONE, include = JsonTypeInfo.As.PROPERTY, property = "health")
public enum Health implements Comparable<Health> {

  HEALTHY("h", 1), WARNING("w", 2), SEVERE("s", 3), UNKNOWN("u", 4);

  private final String code;

  private final Integer severity;

  private Health(String code, Integer severity) {

    this.code = code;
    this.severity = severity;
  }

  public Integer getSeverity() {

    return severity;
  }

  public String getCode() {
    return code;
  }
}
