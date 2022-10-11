package br.com.gransistemas.taurus.model;

public class EventZone {
    private EventType eventType;
    private Zone fence = new Zone();

    public EventType getEventType() { return eventType; }
    public void setEventType(EventType eventType) { this.eventType = eventType; }

    public Zone getZone() { return fence; }
    public void setFence(Zone fence) { this.fence = fence; }
}
