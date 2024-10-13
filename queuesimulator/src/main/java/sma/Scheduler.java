package sma;

import sma.event.Event;
import sma.util.LowPriorityQueue;

public class Scheduler {
    private LowPriorityQueue lowPriorityQueue;

    public Scheduler(LowPriorityQueue lowPriorityQueue) {
        this.lowPriorityQueue = lowPriorityQueue;
    }

    public Event nextEvent() {
        return lowPriorityQueue.pool();
    }

    public void schedule(Event evento) {
        lowPriorityQueue.add(evento);
    }

}
