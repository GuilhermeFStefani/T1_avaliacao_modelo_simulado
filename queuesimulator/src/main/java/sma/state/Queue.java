package sma.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sma.Network;
import sma.interfaces.IInterval;

public class Queue {
    private final String id;
    private final int c; // servidores
    private final int k; // capacidade
    private final IInterval entryInterval;
    private final IInterval exitInterval;
    private List<Network> networks;
    private int currentState = 0;
    private Long losses = 0L;
    private Map<Integer, Float> queueStates;

    public Queue(String id,
            int c,
            int k,
            IInterval exit,
            IInterval entry) {
        this.id = id;
        this.c = c;
        this.k = k;
        this.queueStates = new HashMap<Integer, Float>();
        this.entryInterval = entry;
        this.exitInterval = exit;
    }

    public String getId() {
        return id;
    }

    public int getC() {
        return c;
    }

    public int getK() {
        return k;
    }

    public IInterval getEntryInterval() {
        return entryInterval;
    }

    public IInterval getExitInterval() {
        return exitInterval;
    }

    public List<Network> getNetworks() {
        return networks;
    }

    public void setNetworks(List<Network> networks) {
        this.networks = networks;
    }

    public int getCurrentState() {
        return currentState;
    }

    public void decrementCurrentState() {
        this.currentState--;
    }

    public void incrementCurrentState() {
        this.currentState++;
    }

    public Map<Integer, Float> getQueueStates() {
        return queueStates;
    }

    public void setQueueStates(Map<Integer, Float> estadosFila) {
        this.queueStates = estadosFila;
    }

    public void incrementLosses() {
        this.losses++;
    }

    public Long getLosses() {
        return losses;
    }

    public void setLosses(Long perdas) {
        this.losses = perdas;
    }
}
