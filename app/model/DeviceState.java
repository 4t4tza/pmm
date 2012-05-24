package model;

import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName("DeviceState")
public enum DeviceState {

  ON, OFF, STANDBY;
}
