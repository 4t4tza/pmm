import actor.DeviceStatusMockActor;
import akka.actor.ActorRef;
import com.typesafe.config.ConfigFactory;
import model.mock.MockUpdates;
import model.mock.MuseumBusFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.Application;
import play.GlobalSettings;
import play.api.mvc.Handler;
import play.api.mvc.RequestHeader;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

import java.lang.reflect.Method;

public class Global extends GlobalSettings {

  private static final Logger LOG = LoggerFactory.getLogger(Global.class);

  private static ActorRef devActRef;

  @Override
  public void beforeStart(Application application) {

    super.beforeStart(application);    
  }

  @Override
  public void onStart(Application application) {

    super.onStart(application);
    LOG.info("mb start");
    new MuseumBusFactory().museumBus();
    LOG.info("mu load");
    MockUpdates.getInstance(ConfigFactory.load());
    devActRef = DeviceStatusMockActor.instance;
  }

  @Override
  public void onStop(Application application) {

    super.onStop(application);
    LOG.info("mb stop");
    LOG.info("mu stop");
  }

  @Override
  public Action onRequest(Http.Request request, Method method) {

    return super.onRequest(request,
                           method);    
  }

  @Override
  public Handler onRouteRequest(RequestHeader requestHeader) {

    return super.onRouteRequest(
        requestHeader);    
  }

  @Override
  public Result onError(Throwable error) {

    return super.onError(error);
  }

  @Override
  public Result onHandlerNotFound(String uri) {

    return super.onHandlerNotFound(uri);
  }

  @Override
  public Result onBadRequest(String uri, String error) {

    return super.onBadRequest(uri, error);
  }
}