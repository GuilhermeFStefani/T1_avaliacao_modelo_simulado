package sma.interfaces;

import java.util.Collection;

public record ISimulation(Integer simulationCount,
        Long executionCount,
        Collection<IQueue> queues) {
}
