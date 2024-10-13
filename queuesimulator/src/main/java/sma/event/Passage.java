package sma.event;

import sma.state.Queue;

public class Passage extends Event {
    private final Queue origin;
    private final Queue destiny;

    public Passage(Float eventInstant, Queue origin, Queue destiny) {
        super(eventInstant);
        this.origin = origin;
        this.destiny = destiny;
    }

    public Queue getOrigin() {
        return origin;
    }

    public Queue getDestiny() {
        return destiny;
    }
}
