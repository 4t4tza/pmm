package controllers;

import model.Device;
import model.MuseumBus;
import model.Room;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.errorPage;

public class Devices extends Controller {

  private static final Logger LOG = LoggerFactory.getLogger(Devices.class);

  public static Result device(Long roomNumber, String deviceName) {

    LOG.debug(String.format("device action|room number: %d|device number: %s",roomNumber, deviceName));
    Result result;

    try {

      Device match = MuseumBus.getInstance().roomAt(roomNumber.intValue()).deviceAt(deviceName);

      assert match != null;

      ObjectNode oNode = (ObjectNode) Json.toJson(match);
      oNode.put("params", routes.Devices.device(roomNumber, deviceName).url() + "/params");

      result = ok(oNode);
    } catch (Exception ex) {
      result = badRequest(errorPage.render(ex));
    }

    return result;
  }

  public static Result devices(Long roomNumber) {

    LOG.debug(String.format("devices action|room number: %d", roomNumber));

    Result result;

    try {

      Room room = MuseumBus.getInstance().roomAt(roomNumber.intValue());

      assert room != null;

      ArrayNode aNode = new ObjectMapper().createArrayNode();

      for(Device device : room)
        aNode.add(Json.toJson(device));

      result = ok(aNode);
    } catch (Exception ex) {
      result = badRequest(errorPage.render(ex));
    }

    return result;
  }
}

