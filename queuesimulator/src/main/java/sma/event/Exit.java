package sma.event;

import sma.state.Queue;

public class Exit extends Event {
    private final Queue origin;

    public Exit(Float eventInstant, Queue origin) {
        super(eventInstant);
        this.origin = origin;
    }

    public Queue getOrigin() {
        return origin;
    }
}
