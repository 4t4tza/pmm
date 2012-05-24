package model;

import org.codehaus.jackson.annotate.*;

@JsonTypeName("DeviceParam")
public abstract class DeviceParam <T> {

  private T lastSamplingValue;

  private final DeviceParamValueHistory<T> history;

  protected DeviceParam() {

    this.history = new DeviceParamValueHistory<>();
  }

  @JsonProperty("name")
  public abstract String getName();

  @JsonIgnore
  public abstract T getRangeMax();

  @JsonIgnore
  public abstract T getRangeMin();

  @JsonProperty("lastSample")
  public T getLastSamplingValue() {

    return lastSamplingValue;
  }

  @JsonIgnore
  public DeviceParamValueHistory<T> getHistory() {

    return history;
  }

  public void update(T value) {

    lastSamplingValue = value;
    history.sample(value);
  }

  public void updateError() {

    history.sampleMissing();
  }

  @Override
  public String toString() {

    return "DeviceParam{" +
           "name='" + getName() + "', " +
           "rangeMin=" + getRangeMin() + ", " +
           "rangeMax=" + getRangeMax() + ", " +
           "lastSamplingValue=" + lastSamplingValue + '}';
  }
}
