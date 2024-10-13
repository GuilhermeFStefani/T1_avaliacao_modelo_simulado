package sma.event;

public abstract class Event {
    private final Float eventInstant;

    public Event(Float eventInstant) {
        this.eventInstant = eventInstant;
    }

    public Float getEventInstant() {
        return eventInstant;
    }
}
