package sma;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import sma.event.Entry;
import sma.interfaces.IConfig;
import sma.state.Queue;
import sma.util.ConfigReader;
import sma.util.LowPriorityQueue;
import sma.util.RandomGL;

public class App {
    public static void main(String[] args) throws Exception {
        ConfigReader reader = new ConfigReader();
        String fileName = "queueConfiguration.json";
        IConfig config = reader.readConfig(fileName);

        config.simulations()
                .stream()
                .forEach(simulation -> {
                    Float simulationTime = 0f;
                    Collection<Collection<Queue>> simulations = new ArrayList<Collection<Queue>>();

                    for (int i = 0; i < simulation.simulationCount(); i++) {
                        Set<Queue> queues = simulation.queues()
                                .stream()
                                .map(queue -> new Queue(queue.id(),
                                        queue.servers(),
                                        (queue.capacity() != null ? queue.capacity() : Integer.MAX_VALUE),
                                        queue.exit(),
                                        queue.entry()))
                                .collect(Collectors.toSet());

                        queues.stream()
                                .forEach(queue -> {

                                    List<Network> networks = simulation.queues()
                                            .stream()
                                            .filter(f -> f.id().equals(queue.getId()))
                                            .findFirst()
                                            .get()
                                            .networks()
                                            .stream()
                                            .map(network -> new Network(network.probability(), queues
                                                    .stream()
                                                    .filter(f -> f.getId().equals(network.destiny()))
                                                    .findFirst()))
                                            .collect(Collectors.toList());

                                    queue.setNetworks(networks);
                                });

                        Collection<Entry> entries = simulation.queues()
                                .stream()
                                .filter(queue -> queue.entry() != null)
                                .map(entry -> new Entry(entry.first_entry(),
                                        queues.stream().filter(
                                                queue -> queue.getId().equals(entry.id()))
                                                .findFirst().get()))
                                .collect(Collectors.toList());

                        Raffle raffle = new Raffle(new RandomGL(simulation.executionCount()));

                        Simulator simulator = new Simulator(queues,
                                entries,
                                new Scheduler(new LowPriorityQueue()),
                                raffle,
                                new EventGenerator(raffle),
                                simulation.executionCount());

                        try {
                            simulator.run();
                        } catch (Exception e) {
                        }

                        simulations.add(queues);
                        simulationTime += simulator.getSimulationTime();
                    }

                    Map<String, Queue> averageQueueSimulations = new HashMap<>();
                    for (Collection<Queue> queueList : simulations) {
                        for (Queue queue : queueList) {
                            if (averageQueueSimulations.containsKey(queue.getId())) {

                                Queue newQueue = averageQueueSimulations.get(queue.getId());

                                Map<Integer, Float> newQueueStates = new HashMap<Integer, Float>();

                                for (int i = 0; i < newQueue.getQueueStates().size(); i++) {
                                    newQueueStates.put(i,
                                            newQueue.getQueueStates().get(i) + queue.getQueueStates().get(i));
                                }

                                newQueue.setQueueStates(newQueueStates);
                                newQueue.setLosses(newQueue.getLosses() + queue.getLosses());

                                averageQueueSimulations.put(newQueue.getId(), newQueue);
                            } else {
                                averageQueueSimulations.put(queue.getId(), queue);
                            }

                        }
                    }

                    DecimalFormat df = new DecimalFormat("#.##");
                    df.setRoundingMode(RoundingMode.DOWN);
                    for (Queue fila : averageQueueSimulations.values()) {

                        System.out.println(
                                String.format("\n ---------------- [ %s ] ---------------- \n", fila.getId()));

                        System.out.println("STATE    |     TIME        |    PERCENTAGE");

                        for (int i = 0; i < fila.getQueueStates().size(); i++) {
                            System.out.println(String.format("%s | %s | %s",
                                    alignStrings(Integer.toString(i), 8),
                                    alignStrings(
                                            Float.toString(fila.getQueueStates().get(i) / simulation.simulationCount()),
                                            15),
                                    alignStrings(df.format((fila.getQueueStates().get(i) / simulation.simulationCount())
                                            / (simulationTime / simulation.simulationCount()) * 100) + "%", 20)));
                        }
                        System.out.println(
                                String.format("\nLOSSES: %s", fila.getLosses() / simulation.simulationCount()));
                    }

                    System.out.println(
                            String.format("\nSIMULATION TIME: %s\n", simulationTime / simulation.simulationCount()));
                });
    }

    private static String alignStrings(String s, int size) {
        if (s == null || size <= s.length())
            return s;

        StringBuilder sb = new StringBuilder(size);
        for (int i = 0; i < (size - s.length()) / 2; i++) {
            sb.append(' ');
        }
        sb.append(s);
        while (sb.length() < size) {
            sb.append(' ');
        }
        return sb.toString();
    }
}
