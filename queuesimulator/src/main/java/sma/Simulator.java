package sma;

import java.util.Collection;

import sma.event.Entry;
import sma.event.Event;
import sma.event.Exit;
import sma.event.Passage;
import sma.state.Queue;

public class Simulator {
    public final Collection<Queue> queues;
    private final Collection<Entry> firstEntries;
    private final Scheduler scheduler;
    private final Raffle raffle;
    private final EventGenerator eventGenerator;
    private final Long simulationCount;
    private Float simulationTime;

    public Simulator(Collection<Queue> queues,
            Collection<Entry> initialEntries,
            Scheduler scheduler,
            Raffle raffle,
            EventGenerator eventGenerator,
            Long simulationCount) {
        this.queues = queues;
        this.firstEntries = initialEntries;
        this.scheduler = scheduler;
        this.raffle = raffle;
        this.eventGenerator = eventGenerator;
        this.simulationCount = simulationCount;
        this.simulationTime = 0F;
    }

    private void entry(Entry entry) throws Exception {
        trackTime(queues, entry.getEventInstant());

        if (entry.getDestiny().getCurrentState() < entry.getDestiny().getK()) {

            entry.getDestiny().incrementCurrentState();

            if (entry.getDestiny().getCurrentState() <= entry.getDestiny().getC())
                schedulePassageOrExit(entry.getDestiny());

        } else {
            entry.getDestiny().incrementLosses();
        }

        scheduler.schedule(eventGenerator.newEntry(simulationTime, entry.getDestiny()));
    }

    private void passage(Passage passage) throws Exception {
        trackTime(queues, passage.getEventInstant());

        passage.getOrigin().decrementCurrentState();
        if (passage.getOrigin().getCurrentState() >= passage.getOrigin().getC())
            schedulePassageOrExit(passage.getOrigin());

        if (passage.getDestiny().getCurrentState() < passage.getDestiny().getK()) {
            passage.getDestiny().incrementCurrentState();
            if (passage.getDestiny().getCurrentState() <= passage.getDestiny().getC())
                schedulePassageOrExit(passage.getDestiny());
        } else {
            passage.getDestiny().incrementLosses();
        }
    }

    private void exit(Exit exit) throws Exception {
        trackTime(queues, exit.getEventInstant());

        exit.getOrigin().decrementCurrentState();
        if (exit.getOrigin().getCurrentState() >= exit.getOrigin().getC())
            schedulePassageOrExit(exit.getOrigin());
    }

    private void schedulePassageOrExit(Queue queue) throws Exception {
        Queue destiny = raffle.getNextQueue(queue.getNetworks()).orElse(null);

        if (destiny != null) {
            scheduler.schedule(eventGenerator.newPassage(simulationTime, queue, destiny));
        } else {
            scheduler.schedule(eventGenerator.newExit(simulationTime, queue));
        }
    }

    private void trackTime(Collection<Queue> queues, Float eventInstant) {
        queues.stream().forEach(fila -> trackQueueStates(fila, eventInstant));

        this.simulationTime = eventInstant;
    }

    private void trackQueueStates(Queue queue, float eventInstant) {
        queue.getQueueStates().put(queue.getCurrentState(),
                queue.getQueueStates().getOrDefault(queue.getCurrentState(), 0F) + (eventInstant - simulationTime));
    }

    public void run() throws Exception {
        Long count = 0L;
        firstEntries.forEach(entradaInicial -> scheduler.schedule(entradaInicial));

        while (count < simulationCount) {

            Event nextEvent = scheduler.nextEvent();

            if (nextEvent.getClass().equals(Entry.class))
                entry((Entry) nextEvent);
            if (nextEvent.getClass().equals(Exit.class))
                exit((Exit) nextEvent);
            if (nextEvent.getClass().equals(Passage.class))
                passage((Passage) nextEvent);

            count++;
        }

    }

    public Float getSimulationTime() {
        return simulationTime;
    }
}
