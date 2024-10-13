package sma.util;

import java.util.Comparator;
import java.util.PriorityQueue;

import sma.event.Event;

public class LowPriorityQueue {
    private PriorityQueue<Event> minHeap = new PriorityQueue<>(new Comparator<Event>() {
        @Override
        public int compare(Event o1, Event o2) {
            if (o1.getEventInstant() > (o2.getEventInstant()))
                return +1;
            if (o1.equals(o2))
                return 0;
            return -1;
        }
    });

    public Event pool() {
        return minHeap.poll();
    }

    public void add(Event evento) {
        minHeap.add(evento);
    }
}
