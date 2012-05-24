package actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.util.Duration;
import model.*;
import model.mock.MockUpdates;
import play.Logger;
import play.libs.Akka;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class DeviceStatusMockActor extends UntypedActor {

  private static final String MESSAGE = "statusUpdate";

  private static final Integer INITIAL_DELAY = 500;

  private static final Integer INTER_MESSAGE_SPAN = 500;

  public static ActorRef instance = Akka.system().actorOf(new Props(DeviceStatusMockActor.class));

  static {
    Akka.system().scheduler().schedule(Duration.create(INITIAL_DELAY, MILLISECONDS),
                                       Duration.create(INTER_MESSAGE_SPAN, MILLISECONDS),
                                       instance, MESSAGE);
  }

  public void onReceive(Object message) {

    if(MESSAGE.equals(message)) {

      Logger.trace("updating device params");

      for(Room room : MuseumBus.getInstance()) {

        Logger.trace(String.format("updating device params in room: %d",room.getNo()));

        for(Device device : room) {

          if(device instanceof Projector)
            MockUpdates.getInstance().updateProj((Projector) device);
          else
            MockUpdates.getInstance().updateAmp((Amplifier) device);
        }
      }
    }
  }
}
