package model;

import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName("Projector")
public class Projector extends Device {

  private DeviceParam<Integer> lampTemperature;

  public Projector(String name) {

    super(name);
    lampTemperature=new DeviceParam<Integer>() {

      private static final String PARAM_NAME = "lamp_temperature";

      @Override
      public String getName() {

        return PARAM_NAME;
      }

      @Override
      public Integer getRangeMax() {

        return 100;
      }

      @Override
      public Integer getRangeMin() {

        return 0;
      }
    };
  }

  @Override
  public DeviceType getType() {

    return DeviceType.PROJECTOR;
  }

  @Override
  public DeviceStatus getStatus() {

    return ProjectorStatus.getInstance(lampTemperature.getLastSamplingValue());
  }

  @Override
  public DeviceParam [] getDeviceParams() {

    return new DeviceParam[]{lampTemperature};
  }

  public void updateLampTemperature(Integer lampTemperature) {

    this.lampTemperature.update(lampTemperature);
  }

  public void unknownLampTemperature() {

    this.lampTemperature.updateError();
  }

  @Override
  public String toString() {

    return "Projector{" +
           "lampTemperature=" + lampTemperature + "} "
           + super.toString();
  }
}
