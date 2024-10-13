package sma.util;

public class RandomGL {
    private final Long a = 104729L;
    private final Long c = 11L;
    private final Long M = 2147483647L;
    private Long seed;
    private Long limit;

    public RandomGL(Long limit) {
        this.seed = System.nanoTime();
        this.limit = limit;
    }

    public RandomGL(Long seed, Long limit) {
        this.seed = seed;
        this.limit = limit;
    }

    public void newSeed(Long seed) {
        this.seed = seed;
    }

    public Float nextRandom() throws Exception {

        limit--;
        if (limit == 0)
            throw new Exception("Fim da simulação");

        return Float.parseFloat(random(seed).toString()) / Float.parseFloat(M.toString());
    }

    private Long random(Long seed) {
        this.seed = (a * seed + c) % M;
        return this.seed;
    }
}
