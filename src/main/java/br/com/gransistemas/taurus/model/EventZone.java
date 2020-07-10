package br.com.gransistemas.taurus.model;

public class EventZone {
    private Event event = new Event();
    private Zone fence = new Zone();

    public Event getEvent() { return event; }
    public void setEvent(Event event) { this.event = event; }

    public Zone getZone() { return fence; }
    public void setFence(Zone fence) { this.fence = fence; }
}
