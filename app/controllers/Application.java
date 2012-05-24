package controllers;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

  private static final Logger LOG = LoggerFactory.getLogger(Application.class);

  public static Result index() {

    LOG.debug("loading config: monitor.connection");

    Config conf = ConfigFactory.load();
    String connection = conf.getString("monitor.connection");

    LOG.debug(String.format("monitor.connection: %s", connection));

    Result result;

    if(connection == null || connection.equalsIgnoreCase("push") || connection.equalsIgnoreCase("comet"))
      result = ok(index.render("comet"));
    else if(connection.equalsIgnoreCase("pull") || connection.equalsIgnoreCase("xhr"))
      result = ok(index.render("xhr"));
    else
      result = internalServerError(String.format("[error|config malformed] monitor.connection=%s. " +
                                                 "Legal values include (push|comet|pull|xhr)",connection));

    return result;
  }
}