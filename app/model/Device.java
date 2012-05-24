package model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeName;

import java.util.Collection;

@JsonTypeName("Device")
@JsonSubTypes({@JsonSubTypes.Type(name="Projector", value = Projector.class),
              @JsonSubTypes.Type(name="Amnplifier", value = Amplifier.class)})
public abstract class Device {

  private String name;

  private DeviceState state;

  protected Device(String name, DeviceState state) {

    this.name = name;
    this.state = state;
  }

  protected Device(String name) {

    this(name, DeviceState.OFF);
  }

  @JsonProperty("type")
  public abstract DeviceType getType();

  @JsonIgnore
  public abstract DeviceStatus getStatus();

  @JsonProperty("health")
  public Health getHealth() {
    return getStatus().getHealth();
  }

  @JsonProperty("params")
  public abstract DeviceParam [] getDeviceParams();

  @JsonProperty("name")
  public String getName() {

    return name;
  }

  @JsonProperty("state")
  public DeviceState getState() {

    return state;
  }

  @Override
  public String toString() {

    return "Device{" +
           "name='" + name + '\'' +
           ", state=" + state +
           '}';
  }
}
