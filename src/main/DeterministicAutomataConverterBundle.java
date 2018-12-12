package main;

import java.util.ArrayList;
import java.util.List;

public class DeterministicAutomataConverterBundle {

    private State initialState;
    private List<State> states = new ArrayList<>();
    private List<State> finalStates = new ArrayList<>();
    private List<String> alphabet = new ArrayList<>();
    private List<DeterministicStateTransition> stateTransitions = new ArrayList<>();

    public State getInitialState() {
        return initialState;
    }

    public void setInitialState(State initialState) {
        this.initialState = initialState;
    }

    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }

    public List<State> getFinalStates() {
        return finalStates;
    }

    public void setFinalStates(List<State> finalStates) {
        this.finalStates = finalStates;
    }

    public List<String> getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(List<String> alphabet) {
        this.alphabet = alphabet;
    }

    public List<DeterministicStateTransition> getStateTransitions() {
        return stateTransitions;
    }

    public void setStateTransitions(List<DeterministicStateTransition> stateTransitions) {
        this.stateTransitions = stateTransitions;
    }
}
