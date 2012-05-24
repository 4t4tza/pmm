package controllers;

import actor.RoomsActor;
import akka.actor.ActorRef;
import model.MuseumBus;
import model.Room;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.libs.Comet;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.errorPage;

public class Rooms extends Controller {

  private final static Logger LOG = LoggerFactory.getLogger(Rooms.class);

  private final static ActorRef devActRef = RoomsActor.instance;

  public static Result cometRooms() {

    LOG.debug("comet Rooms action");

    return ok(new Comet("parent.cometRooms") {

      public void onConnected() {

        LOG.debug("comet connected");

        devActRef.tell(this);
      }
    });
  }

  public static Result rooms() {

    LOG.debug("rooms action");

    ArrayNode an = new ObjectMapper().createArrayNode();

    for (Room room : MuseumBus.getInstance()) {

      ObjectNode roomNode = (ObjectNode) Json.toJson(room);
      roomNode.put("devices", routes.Rooms.room(room.getNo()).url() + "/devices");
      an.add(roomNode);
    }

    return ok(an);
  }

  public static Result room(Long number) {

    LOG.debug(String.format("room action|room number: %d", number));

    Result result;

    try {

      Room room = MuseumBus.getInstance().roomAt(number.intValue());

      assert room != null;

      LOG.debug("selected room: {}", room);

      ObjectNode oNode = (ObjectNode) Json.toJson(room);
      oNode.put("devices", routes.Rooms.room(number).url() + "/devices");

      result = ok(oNode);
    } catch (Exception ex) {
      result = badRequest(errorPage.render(ex));
    }

    return result;
  }
}
