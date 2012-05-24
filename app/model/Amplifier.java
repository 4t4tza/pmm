package model;

import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName("Amplifier")
public class Amplifier extends Device {

  private DeviceParam<Double> fanFrequency;

  public Amplifier(String name) {

    super(name);
    fanFrequency = new DeviceParam<Double>() {

      private static final String PARAM_NAME = "fan_frequency_percentage";

      @Override
      public String getName() {

        return PARAM_NAME;
      }

      @Override
      public Double getRangeMax() {

        return 100.0;
      }

      @Override
      public Double getRangeMin() {

        return 0.0;
      }
    };
  }

  @Override
  public DeviceType getType() {

    return DeviceType.AMPLIFIER;
  }

  @Override
  public DeviceStatus getStatus() {

    return AmplifierStatus.getInstance(fanFrequency.getLastSamplingValue());
  }

  @Override
  public DeviceParam[] getDeviceParams() {

    return new DeviceParam[]{fanFrequency};
  }

  public void updateFanFrequency(Double fanFrequency) {

    this.fanFrequency.update(fanFrequency);
  }

  public void unknownFanFrequency() {
    this.fanFrequency.updateError();
  }

  @Override
  public String toString() {

    return "Amplifier{" +
           "fanFrequency=" + fanFrequency +
           "} " + super.toString();
  }
}
