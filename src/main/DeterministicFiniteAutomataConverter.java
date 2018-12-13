package main;

import main.automata.*;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class DeterministicFiniteAutomataConverter {

    private Map<State, Boolean> statesToProcess = new HashMap<>();
    private List<NondeterministicStateTransition> nondeterministicTransitions;
    private NondeterministicFiniteAutomata automata;

    public DeterministicFiniteAutomataConverter(NondeterministicFiniteAutomata automata) {
        this.automata = automata;
        nondeterministicTransitions = automata.getStateTransitions();
    }

    public DeterministicFiniteAutomata convert() {

        DeterministicAutomataLanguageBundle bundle = new DeterministicAutomataLanguageBundle();

        statesToProcess.put(automata.getInitialState(), false);

        List<DeterministicStateTransition> transitions = new ArrayList<>();
        List<String> alphabet = automata.getAlphabet();

        while (hasUnprocessedState()) {
            State state = getUnprocessedState();

            // "ABC" -> ["A", "B"]
            List<String> miniStates = Arrays.stream(state.toString().split("")).collect(toList());

            for (String symbol : alphabet) {

                Stream<NondeterministicStateTransition> transitionsForCurrentSymbol = nondeterministicTransitions.stream()
                        .filter(t -> t.getSymbol().equals(symbol));

                Predicate<NondeterministicStateTransition> currentlyProcessedStateIsSuperSetOfState =
                        t -> miniStates.contains(t.getFrom().toString());

                // "A + 0 -> ABC U BD => A + 0 -> ABCD
                List<String> transitionResultsForCurrentStateAndSymbol = transitionsForCurrentSymbol
                        .filter(currentlyProcessedStateIsSuperSetOfState)
                        .map(NondeterministicStateTransition::getTo)
                        .flatMap(Collection::stream)
                        .distinct()
                        .map(Object::toString)
                        .sorted()
                        .collect(toList());

                if (!transitionResultsForCurrentStateAndSymbol.isEmpty()) {

                    State nextState = new State(String.join("", transitionResultsForCurrentStateAndSymbol));
                    addToStatesSet(nextState);

                    DeterministicStateTransition transition = new DeterministicStateTransition();
                    transition.setFrom(state);
                    transition.setSymbol(symbol);
                    transition.setTo(nextState);

                    transitions.add(transition);
                }
            }
        }


        String nondeterministicAutomataFinalStateTitle =
                automata.getFinalStates().stream()
                        .map(Object::toString)
                        .sorted()
                        .collect(joining());


        List<State> finalStates = transitions.stream()
                .map(DeterministicStateTransition::getFrom)
                .distinct()
                .filter(t -> t.toString().contains(nondeterministicAutomataFinalStateTitle))
                .collect(toList());

        bundle.setFinalStates(finalStates);
        bundle.setAlphabet(automata.getAlphabet());
        bundle.setInitialState(automata.getInitialState());
        bundle.setStateTransitions(transitions);
        bundle.setStates(new ArrayList<>(statesToProcess.keySet()));

        return new DeterministicFiniteAutomata(bundle);
    }

    private boolean hasUnprocessedState() {
        // if the boolean value of the map is false, it means that the state hasn't been processed yet
        return statesToProcess.values().stream().anyMatch(value -> !value);
    }

    private State getUnprocessedState() {
        Map.Entry<State, Boolean> unprocessedState = statesToProcess.entrySet().stream()
                .filter(entry -> !entry.getValue())
                .findFirst().get();

        // also "marks" it as visited
        unprocessedState.setValue(true);

        return unprocessedState.getKey();
    }

    private boolean addToStatesSet(State state) {
        if (!statesToProcess.keySet().contains(state)) {
            statesToProcess.put(state, false);
            return true;
        }
        return false;
    }
}