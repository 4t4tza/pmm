package model.mock;

import com.typesafe.config.Config;
import model.Amplifier;
import model.DeviceParam;
import model.Projector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Random;

public class MockUpdates {

  private static final Logger LOG = LoggerFactory.getLogger(MockUpdates.class);

  private static MockUpdates singletonMockUpdates = null;

  int ampRaise;

  int ampLower;

  int ampSuccess;

  int ampError;

  int ampDeltaMax;

  int ampDeltaMin;

  int projRaise;

  int projLower;

  int projSuccess;

  int projError;

  int projDeltaMax;

  int projDeltaMin;

  Random random;

  public static MockUpdates getInstance() {
    return singletonMockUpdates;
  }

  public static MockUpdates getInstance(Config config) {

    if(singletonMockUpdates == null)
      singletonMockUpdates = new MockUpdates(config);

    return singletonMockUpdates;
  }

  private MockUpdates(Config config) {

    random = new Random(new Date().getTime());
    init(config);
  }

  private <T extends Comparable<T>> T clamp(T min, T max, T value) {

    assert min != null;
    assert max != null;
    assert value != null;

    assert min.compareTo(max) < 0;

    T result;

    if (value.compareTo(min) < 0)
      result = min;
    else if (value.compareTo(max) > 0)
      result = max;
    else
      result = value;

    return result;
  }

  private void init(Config config) {

    LOG.debug("MU init!");

    ampRaise = config.getInt("monitor.mock.amplifier.coefficients.raise");
    ampLower = config.getInt("monitor.mock.amplifier.coefficients.lower");
    ampSuccess = config.getInt("monitor.mock.amplifier.coefficients.success");
    ampError = config.getInt("monitor.mock.amplifier.coefficients.error");
    ampDeltaMax = config.getInt("monitor.mock.amplifier.coefficients.delta.max");
    ampDeltaMin = config.getInt("monitor.mock.amplifier.coefficients.delta.min");
    projRaise = config.getInt("monitor.mock.projector.coefficients.raise");
    projLower = config.getInt("monitor.mock.projector.coefficients.lower");
    projSuccess = config.getInt("monitor.mock.projector.coefficients.success");
    projError = config.getInt("monitor.mock.projector.coefficients.error");
    projDeltaMax = config.getInt("monitor.mock.projector.coefficients.delta.max");
    projDeltaMin = config.getInt("monitor.mock.projector.coefficients.delta.min");

    assert ampRaise > 0;
    assert ampLower > 0;
    assert ampSuccess > 0;
    assert ampError > 0;
    assert ampDeltaMin > 0;
    assert ampDeltaMax > ampDeltaMin;
    assert projRaise > 0;
    assert projLower > 0;
    assert projSuccess > 0;
    assert projError > 0;
    assert projDeltaMin > 0;
    assert projDeltaMax > projDeltaMin;

    LOG.debug("MU init successful");
  }

  public void updateAmp(Amplifier amplifier) {

    @SuppressWarnings("unchecked")
    DeviceParam<Double> fanFreq = amplifier.getDeviceParams()[0];

    if (random.nextInt(ampError + ampSuccess) < ampError)
      amplifier.unknownFanFrequency();
    else {
      int delta = random.nextInt(ampDeltaMax - ampDeltaMin) + ampDeltaMin;

      Double rangeMax = fanFreq.getRangeMax();
      Double rangeMin = fanFreq.getRangeMin();
      Double realDelta = (double) delta;

      realDelta = random.nextInt(ampRaise + ampLower) < ampRaise ? realDelta : -(realDelta);

      Double currentValue = fanFreq.getLastSamplingValue();
      Double clampedValue = clamp(rangeMin, rangeMax, currentValue + realDelta);

      LOG.trace(String.format("amp fan frequency update: [current:%f, new:%f, delta:%f]",
                              currentValue, clampedValue, realDelta));

      amplifier.updateFanFrequency(clampedValue);
    }
  }

  public void updateProj(Projector projector) {

    @SuppressWarnings("unchecked")
    DeviceParam<Integer> lampTemp = projector.getDeviceParams()[0];

    if (random.nextInt(projError + projSuccess) < projError)
      projector.unknownLampTemperature();
    else {
      int delta = random.nextInt(projDeltaMax - projDeltaMin) + projDeltaMin;

      delta = random.nextInt(projRaise + projLower) < projRaise ? delta : -delta;

      Integer rangeMax = lampTemp.getRangeMax();
      Integer rangeMin = lampTemp.getRangeMin();

      Integer currentValue = lampTemp.getLastSamplingValue();
      Integer clampedValue = clamp(rangeMin, rangeMax, currentValue + delta);

      LOG.trace(String.format("projector lamp temperature update: [current:%d, new:%d, delta:%d]",
                              currentValue, clampedValue, delta));

      projector.updateLampTemperature(clampedValue);
    }
  }
}
