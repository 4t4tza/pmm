package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class MuseumBus implements Iterable <Room> {

  private static final Integer ROOMS_IN_A_ROW = 3;

  private static final Integer ROOMS_IN_A_COLUMN = 3;

  private static MuseumBus museumBus;

  public static MuseumBus getInstance() {
    if(museumBus == null)
      museumBus = new MuseumBus();

    return museumBus;
  }

  public Room roomAt(Integer roomNumber) {

    Room match = null;

    for(Room room : rooms)
      if(room.getNo().equals(roomNumber)) {

        match = room;
        break;
      }

    if(match == null)
      throw new IllegalArgumentException("illegal number, no such room");

    return match;
  }

  private Collection<Room> rooms;

  private MuseumBus() {

    this.rooms = new ArrayList<>(ROOMS_IN_A_ROW * ROOMS_IN_A_COLUMN);
  }

  @Override
  public Iterator<Room> iterator() {

    return rooms.iterator();
  }

  public void addRoom(Room room) {

    if(rooms.size() == ROOMS_IN_A_COLUMN * ROOMS_IN_A_ROW)
      throw new IllegalStateException("room limit exceeded");

    rooms.add(room);
  }

  @Override
  public String toString() {

    return "MuseumBus{" +
           "rooms=" + rooms +
           '}';
  }
}
