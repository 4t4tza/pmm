package model;

public class AmplifierStatus implements DeviceStatus {

  private final Double amplifierFanFrequencyInPercent;

  public static AmplifierStatus getInstance(Double amplifierFanFrequency) {

    if(amplifierFanFrequency < 0 || amplifierFanFrequency > 100)
      throw new IllegalArgumentException("Illegal frequency value. Expected values: f c [0, 100]");

    return new AmplifierStatus(amplifierFanFrequency);
  }

  private AmplifierStatus(Double amplifierFanFrequencyInPercent) {

    this.amplifierFanFrequencyInPercent = amplifierFanFrequencyInPercent;
  }

  @Override
  public Health getHealth() {

    return amplifierFanFrequencyInPercent == 0 || amplifierFanFrequencyInPercent > 90 ? Health.SEVERE :
           amplifierFanFrequencyInPercent > 70 ? Health.WARNING : Health.HEALTHY;
  }
}
