package actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.util.Duration;
import model.MuseumBus;
import model.Room;
import play.Logger;
import play.libs.Akka;
import play.libs.Comet;
import play.libs.F;
import play.libs.Json;

import java.util.ArrayList;
import java.util.List;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class RoomsActor extends UntypedActor {

  private static final String MESSAGE = "deviceMonitorTick";

  private static final Integer INITIAL_DELAY = 500;

  private static final Integer INTER_MESSAGE_SPAN = 5000;

  public static ActorRef instance = Akka.system().actorOf(new Props(RoomsActor.class));

  static {
    Akka.system().scheduler().schedule(Duration.create(INITIAL_DELAY, MILLISECONDS),
                                       Duration.create(INTER_MESSAGE_SPAN, MILLISECONDS),
                                       instance, MESSAGE);
  }

  private List<Comet> sockets = new ArrayList<Comet>();

  public void onReceive(Object message) {

    if(message instanceof Comet) {

      final Comet cometSocket = (Comet)message;

      if(sockets.contains(cometSocket)) { // Browser is disconnected

        sockets.remove(cometSocket);
        Logger.info("Browser disconnected (" + sockets.size() + " browsers currently connected)");

      } else { // Register disconnected callback

        cometSocket.onDisconnected(new F.Callback0() {
          public void invoke() {
            getContext().self().tell(cometSocket);
          }
        });

        sockets.add(cometSocket);
        Logger.info("New browser connected (" + sockets.size() + " browsers currently connected)");
      }
    }

    if(MESSAGE.equals(message)) { //send to all connected browsers

      for(Comet cometSocket: sockets)
        for(Room room : MuseumBus.getInstance())
          cometSocket.sendMessage(Json.toJson(room));
    }
  }
}
