package model;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

@JsonTypeName("Room")
public class Room implements Iterable <Device> {

  private static final Integer ROOMS_IN_A_ROW = 3;

  private Integer xIndex;

  private Integer yIndex;

  private final Collection<Device> devices;

  public Room(Integer xIndex, Integer yIndex, Collection<? extends Device> devices) {

    this.xIndex = xIndex;
    this.yIndex = yIndex;
    this.devices = new ArrayList<>(devices);
  }

  private RoomStatus getStatus() {

    Collection<DeviceStatus> deviceStatuses = new ArrayList<>(devices.size());

    for (Device device : devices)
      deviceStatuses.add(device.getStatus());

    return new RoomStatus(deviceStatuses);
  }

  public Device deviceAt(String deviceName) {
    Device match = null;

    for(Device device : devices)
      if(device.getName().equals(deviceName)) {

        match = device;
        break;
      }

    if(match == null)
      throw new IllegalArgumentException("illegal device, no such device");

    return match;
  }

  @JsonProperty("x")
  public Integer getXIndex() {

    return xIndex;
  }

  @JsonProperty("y")
  public Integer getYIndex() {

    return yIndex;
  }

  @JsonProperty("no")
  public Integer getNo() {

    return ROOMS_IN_A_ROW * yIndex + xIndex;
  }

  @JsonProperty("health")
  public Health getHealth() {

    return getStatus().getHealth();
  }

  @Override
  public Iterator<Device> iterator() {

    return devices.iterator();
  }

  @JsonTypeInfo(use = JsonTypeInfo.Id.NONE, include = JsonTypeInfo.As.PROPERTY, property = "health")
  public static class RoomStatus implements Iterable<DeviceStatus> {

    private Collection<DeviceStatus> deviceStatuses;

    private static final Comparator<Health> comparator;

    static {
      comparator = new HealthComparator();
    }

    private RoomStatus(Collection<? extends DeviceStatus> deviceStatuses) {

      this.deviceStatuses = new ArrayList<>(deviceStatuses);
    }

    @Override
    public Iterator<DeviceStatus> iterator() {

      return deviceStatuses.iterator();
    }

    public Health getHealth() {

      Health health = Health.HEALTHY;

      Iterator<DeviceStatus> dsIterator = deviceStatuses.iterator();

      while (health != Health.SEVERE && dsIterator.hasNext()) {

        DeviceStatus deviceStatus = dsIterator.next();

        health = comparator.compare(health, deviceStatus.getHealth()) < 0 ? deviceStatus.getHealth() : health;
      }

      return health;
    }

    private static final class HealthComparator implements Comparator<Health> {

      @Override
      public int compare(Health h1, Health h2) {

        return h1.getSeverity() - h2.getSeverity();
      }
    }
  }

  @Override
  public String toString() {

    return "Room{" +
           "xIndex=" + xIndex +
           ", yIndex=" + yIndex +
           ", devices=" + devices +
           '}';
  }
}
