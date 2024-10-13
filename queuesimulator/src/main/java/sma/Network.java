package sma;

import java.util.Optional;

import sma.state.Queue;

public class Network implements Comparable<Network> {
    private final Float probability;
    private final Optional<Queue> destiny;

    public Network(Float probability, Optional<Queue> destiny) {
        this.probability = probability;
        this.destiny = destiny;
    }

    public Float getProbability() {
        return probability;
    }

    public Optional<Queue> getDestiny() {
        return destiny;
    }

    @Override
    public int compareTo(Network t) {
        return this.probability.compareTo(t.probability);
    }

}
