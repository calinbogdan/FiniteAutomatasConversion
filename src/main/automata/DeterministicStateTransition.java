package main.automata;

public class DeterministicStateTransition {
    private State from;
    private State to;
    private String symbol;

    public DeterministicStateTransition() {

    }

    public DeterministicStateTransition(State state, State toState, String symbol) {
        this.from = state;
        this.to = toState;
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return String.format("(%s + %s -> %s)", from, symbol, to);
    }

    public State getFrom() {
        return from;
    }

    public void setFrom(State from) {
        this.from = from;
    }

    public State getTo() {
        return to;
    }

    public void setTo(State to) {
        this.to = to;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
