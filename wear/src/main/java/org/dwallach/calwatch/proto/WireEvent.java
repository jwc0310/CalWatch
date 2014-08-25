// Code generated by Wire protocol buffer compiler, do not edit.
// Source file: src/main/java/org/dwallach/calwatch/proto/calendar.proto
package org.dwallach.calwatch.proto;

import com.squareup.wire.Message;
import com.squareup.wire.ProtoField;

import static com.squareup.wire.Message.Datatype.INT32;
import static com.squareup.wire.Message.Datatype.INT64;
import static com.squareup.wire.Message.Label.REQUIRED;

public final class WireEvent extends Message {

  public static final Long DEFAULT_STARTTIME = 0L;
  public static final Long DEFAULT_ENDTIME = 0L;
  public static final Integer DEFAULT_EVENTCOLOR = 0;
  public static final Integer DEFAULT_MINLEVEL = 0;
  public static final Integer DEFAULT_MAXLEVEL = 0;

  @ProtoField(tag = 1, type = INT64, label = REQUIRED)
  public final Long startTime;

  @ProtoField(tag = 2, type = INT64, label = REQUIRED)
  public final Long endTime;

  @ProtoField(tag = 3, type = INT32, label = REQUIRED)
  public final Integer eventColor;

  @ProtoField(tag = 4, type = INT32, label = REQUIRED)
  public final Integer minLevel;

  @ProtoField(tag = 5, type = INT32, label = REQUIRED)
  public final Integer maxLevel;

  public WireEvent(Long startTime, Long endTime, Integer eventColor, Integer minLevel, Integer maxLevel) {
    this.startTime = startTime;
    this.endTime = endTime;
    this.eventColor = eventColor;
    this.minLevel = minLevel;
    this.maxLevel = maxLevel;
  }

  private WireEvent(Builder builder) {
    this(builder.startTime, builder.endTime, builder.eventColor, builder.minLevel, builder.maxLevel);
    setBuilder(builder);
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if (!(other instanceof WireEvent)) return false;
    WireEvent o = (WireEvent) other;
    return equals(startTime, o.startTime)
        && equals(endTime, o.endTime)
        && equals(eventColor, o.eventColor)
        && equals(minLevel, o.minLevel)
        && equals(maxLevel, o.maxLevel);
  }

  @Override
  public int hashCode() {
    int result = hashCode;
    if (result == 0) {
      result = startTime != null ? startTime.hashCode() : 0;
      result = result * 37 + (endTime != null ? endTime.hashCode() : 0);
      result = result * 37 + (eventColor != null ? eventColor.hashCode() : 0);
      result = result * 37 + (minLevel != null ? minLevel.hashCode() : 0);
      result = result * 37 + (maxLevel != null ? maxLevel.hashCode() : 0);
      hashCode = result;
    }
    return result;
  }

  public static final class Builder extends Message.Builder<WireEvent> {

    public Long startTime;
    public Long endTime;
    public Integer eventColor;
    public Integer minLevel;
    public Integer maxLevel;

    public Builder() {
    }

    public Builder(WireEvent message) {
      super(message);
      if (message == null) return;
      this.startTime = message.startTime;
      this.endTime = message.endTime;
      this.eventColor = message.eventColor;
      this.minLevel = message.minLevel;
      this.maxLevel = message.maxLevel;
    }

    public Builder startTime(Long startTime) {
      this.startTime = startTime;
      return this;
    }

    public Builder endTime(Long endTime) {
      this.endTime = endTime;
      return this;
    }

    public Builder eventColor(Integer eventColor) {
      this.eventColor = eventColor;
      return this;
    }

    public Builder minLevel(Integer minLevel) {
      this.minLevel = minLevel;
      return this;
    }

    public Builder maxLevel(Integer maxLevel) {
      this.maxLevel = maxLevel;
      return this;
    }

    @Override
    public WireEvent build() {
      checkRequiredFields();
      return new WireEvent(this);
    }
  }
}
