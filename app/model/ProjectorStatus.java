package model;

public class ProjectorStatus implements DeviceStatus {

  private final Integer lampTemperature;

  public static ProjectorStatus getInstance(Integer lampTemperature) {

    return new ProjectorStatus(lampTemperature);
  }

  private ProjectorStatus(Integer lampTemperature) {

    this.lampTemperature = lampTemperature;
  }

  @Override
  public Health getHealth() {

    return lampTemperature > 80 ? Health.SEVERE : lampTemperature > 60 ? Health.WARNING : Health.HEALTHY;
  }
}
