package sma;

import java.util.List;
import java.util.Optional;

import sma.interfaces.IInterval;
import sma.state.Queue;
import sma.util.RandomGL;

public class Raffle {
    private RandomGL random;

    public Raffle(RandomGL random) {
        this.random = random;
    }

    public Optional<Queue> getNextQueue(List<Network> networks) throws Exception {

        Float randomValue = random.nextRandom();
        Float percentageRange = 0F;

        for (Network network : networks) {
            percentageRange += network.getProbability();
            if (randomValue <= percentageRange)
                return network.getDestiny();
        }

        return Optional.empty();
    }

    public Float instant(IInterval interval) throws Exception {
        return convertValues(interval.min(), interval.max(), random.nextRandom());
    }

    // U(A, B) = (B – A) x U(0, 1) + A
    private Float convertValues(Float a, Float b, Float r) {
        return ((b - a) * r) + a;
    }
}
