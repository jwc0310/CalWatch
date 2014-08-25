// Code generated by Wire protocol buffer compiler, do not edit.
// Source file: src/main/java/org/dwallach/calwatch/proto/calendar.proto
package org.dwallach.calwatch.proto;

import com.squareup.wire.Message;
import com.squareup.wire.ProtoField;
import java.util.Collections;
import java.util.List;

import static com.squareup.wire.Message.Label.REPEATED;

public final class WireEventList extends Message {

  public static final List<WireEvent> DEFAULT_EVENTS = Collections.emptyList();

  @ProtoField(tag = 1, label = REPEATED)
  public final List<WireEvent> events;

  public WireEventList(List<WireEvent> events) {
    this.events = immutableCopyOf(events);
  }

  private WireEventList(Builder builder) {
    this(builder.events);
    setBuilder(builder);
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if (!(other instanceof WireEventList)) return false;
    return equals(events, ((WireEventList) other).events);
  }

  @Override
  public int hashCode() {
    int result = hashCode;
    return result != 0 ? result : (hashCode = events != null ? events.hashCode() : 1);
  }

  public static final class Builder extends Message.Builder<WireEventList> {

    public List<WireEvent> events;

    public Builder() {
    }

    public Builder(WireEventList message) {
      super(message);
      if (message == null) return;
      this.events = copyOf(message.events);
    }

    public Builder events(List<WireEvent> events) {
      this.events = checkForNulls(events);
      return this;
    }

    @Override
    public WireEventList build() {
      return new WireEventList(this);
    }
  }
}
