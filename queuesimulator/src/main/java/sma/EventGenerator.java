package sma;

import sma.event.Entry;
import sma.event.Exit;
import sma.event.Passage;
import sma.state.Queue;

public class EventGenerator {
    private final Raffle raffle;

    public EventGenerator(Raffle raffle) {
        this.raffle = raffle;
    }

    public Entry newEntry(Float currentTime, Queue destiny) throws Exception {
        return new Entry(currentTime + raffle.instant(destiny.getEntryInterval()), destiny);
    }

    public Passage newPassage(Float currentTime, Queue origin, Queue destiny) throws Exception {
        return new Passage(currentTime + raffle.instant(origin.getExitInterval()), origin, destiny);
    }

    public Exit newExit(Float currentTime, Queue origin) throws Exception {
        return new Exit(currentTime + raffle.instant(origin.getExitInterval()), origin);
    }
}
