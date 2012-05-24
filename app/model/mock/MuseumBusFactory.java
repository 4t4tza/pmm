package model.mock;

import model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

public class MuseumBusFactory {

  private Random random;

  private Integer projectorCount;

  private Integer amplifierCount;

  public MuseumBusFactory() {

    this.random = new Random(new Date().getTime());
    this.projectorCount = 0;
    this.amplifierCount = 0;
  }

  public MuseumBus museumBus() {

    MuseumBus mb = MuseumBus.getInstance();

    for(int x = 0; x < 3; x++)
      for(int y = 0; y < 3; y++)
        mb.addRoom(newRoom(x,y));

    return mb;
  }

  private Room newRoom(Integer x, Integer y) {

    Integer devicesCount = random.nextInt(3) + 1;

    Collection<Device>devices = new ArrayList<>(devicesCount);

    for(int i = 0; i < devicesCount; i++)
      devices.add(newDevice());

    return new Room(x, y, devices);
  }

  private Device newDevice() {

    DeviceType type = random.nextBoolean() ? DeviceType.PROJECTOR : DeviceType.AMPLIFIER;

    Device device = null;

    switch (type) {
      case PROJECTOR:
        device = newProjector();
        break;
      case AMPLIFIER:
        device = newAmplifier();
        break;
    }
    return device;
  }

  private Device newProjector() {

    Projector projector = new Projector(String.format("projector-%d", projectorCount++));
    projector.updateLampTemperature(random.nextInt(50) + 5);
    return projector;
  }

  private Device newAmplifier() {

    Amplifier amplifier = new Amplifier(String.format("amplifier-%d", amplifierCount++));
    amplifier.updateFanFrequency(random.nextDouble() * 50.0 + 1.0);
    return amplifier;
  }
}
