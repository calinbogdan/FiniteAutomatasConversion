package main.automata;

public enum WordValidationResult {
    Accepted("acceptat"),
    Blocked("blocat"),
    Rejected("respins");

    private final String display;

    WordValidationResult(String display) {
        this.display = display;
    }

    @Override
    public String toString() {
        return display;
    }
}
