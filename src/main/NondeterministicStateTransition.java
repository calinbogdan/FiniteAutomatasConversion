package main;

import java.util.List;

public class NondeterministicStateTransition {

    private State from;
    private List<State> to;
    private String symbol;

    public NondeterministicStateTransition(State state, List<State> toStates, String symbol) {
        this.from = state;
        this.to = toStates;
        this.symbol = symbol;
    }

    public State getFrom() {
        return from;
    }

    public void setFrom(State from) {
        this.from = from;
    }

    public List<State> getTo() {
        return to;
    }

    public void setTo(List<State> to) {
        this.to = to;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
