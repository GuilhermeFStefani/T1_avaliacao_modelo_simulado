package sma.event;

import sma.state.Queue;

public class Entry extends Event {
    private final Queue destiny;

    public Entry(Float instant, Queue destiny) {
        super(instant);
        this.destiny = destiny;
    }

    public Queue getDestiny() {
        return destiny;
    }
}
