package model;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName("DeviceType")
public enum DeviceType {

  PROJECTOR("proj"), AMPLIFIER("amp");

  @JsonProperty("type")
  private String value;

  private DeviceType(String value) {

    this.value = value;
  }
}
