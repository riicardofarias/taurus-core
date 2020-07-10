package br.com.gransistemas.taurus.model;

import java.util.Objects;
import java.util.StringJoiner;

public class Event extends BaseModel {
    private double speedLimit;
    private EventType eventType;

    public Event() { }
    public Event(EventType eventType) {
        this.eventType = eventType;
    }

    public double getSpeedLimit() { return speedLimit; }
    public void setSpeedLimit(double speedLimit) { this.speedLimit = speedLimit; }

    public EventType getEventType() { return eventType; }
    public void setEventType(EventType eventType) { this.eventType = eventType; }

    @Override
    public String toString() {
        return new StringJoiner(", ", Event.class.getSimpleName() + "[", "]")
            .add("id=" + getId()).add("type=" + eventType)
        .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        return eventType == event.getEventType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventType);
    }
}
