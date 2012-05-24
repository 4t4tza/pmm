package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class DeviceParamValueHistory<T> implements Iterable <DeviceParamValueHistory.Sample<T>> {

  private final List<Sample<T>> samples;

  private Date lastSuccessfulSampling;

  public DeviceParamValueHistory() {

    this.samples = new ArrayList<>();
  }

  public void sampleMissing() {

    samples.add(new Sample<T>(SampleStatus.MISSING, null, new Date()));
  }

  public void sample(T sampleValue) {

    Date currentDate = new Date();
    samples.add(new Sample<>(SampleStatus.PRESENT, sampleValue, currentDate));
    lastSuccessfulSampling = currentDate;
  }

  @Override
  public Iterator<Sample<T>> iterator() {

    return samples.iterator();
  }

  public static class Sample<T> {

    private final SampleStatus status;

    private final T value;

    private final Date date;

    private Sample(SampleStatus status, T value, Date date) {

      this.status = status;
      this.value = value;
      this.date = date;
    }

    public SampleStatus getStatus() {

      return status;
    }

    public T getValue() {

      return value;
    }

    public Long getTimestamp() {

      return date.getTime();
    }
  }

  public static enum SampleStatus {
    PRESENT, MISSING
  }

  public Long getLastSuccessfulSamplingTimestamp() {

    return lastSuccessfulSampling != null ? lastSuccessfulSampling.getTime() : 0;
  }
}
