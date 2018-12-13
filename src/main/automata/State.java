package main.automata;

public class State implements Comparable<State> {

    private static final String INITIAL_STATE_SYMBOL = "-";
    private static final String FINAL_STATE_SYMBOL = "+";
    private static final String BLANK_SYMBOL = "";

    private String symbol;
    private boolean isFinal = false;
    private boolean isInitial = false;

    public State(String rawText) {
        if (rawText.contains(INITIAL_STATE_SYMBOL)) {
            isInitial = true;
        } else if (rawText.contains(FINAL_STATE_SYMBOL)) {
            isFinal = true;
        }

        symbol = rawText.replace(INITIAL_STATE_SYMBOL, BLANK_SYMBOL)
                    .replace(FINAL_STATE_SYMBOL, BLANK_SYMBOL);
    }

    public State(State state) {
        this.symbol = state.symbol;
        this.isInitial = state.isInitial;
        this.isFinal = state.isFinal;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public boolean isInitial() {
        return isInitial;
    }

    @Override
    public String toString() {
        return symbol;
    }

    public String toString(boolean scriptic) {
        return symbol + (isInitial() ? INITIAL_STATE_SYMBOL : BLANK_SYMBOL) + (isFinal() ? FINAL_STATE_SYMBOL : BLANK_SYMBOL);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof State) {
            State state2 = (State) obj;

            if (state2.symbol.equals(this.symbol)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int sum = 0;

        for (char chr : symbol.toCharArray()) {
            sum += Character.getNumericValue(chr);
        }

        return sum;
    }

    @Override
    public int compareTo(State o) {
        return this.symbol.equals(o.symbol) ? 0 : 1;
    }

    public void setInitial(boolean isInitial) {
        this.isInitial = isInitial;
    }

    public boolean contains(State finalState) {
        return toString().contains(finalState.toString());
    }

    public void setFinal(boolean isFinal) {
        this.isFinal = isFinal;
    }
}
