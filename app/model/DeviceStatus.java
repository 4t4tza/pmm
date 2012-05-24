package model;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName("DeviceStatus")
public interface DeviceStatus {

  @JsonProperty("health")
  Health getHealth();
}
