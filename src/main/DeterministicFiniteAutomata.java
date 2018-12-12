package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * In the source file, the first line is made up of the alphabet characters, separated by a space.
 * On the following lines, the state transitions are listed.
 * There's a table being formed, so
 */

public class DeterministicFiniteAutomata implements Automatable {

    private List<String> alphabet;
    private List<State> states;
    private State initialState;
    private List<State> finalStates;
    private List<DeterministicStateTransition> stateTransitions;

    public DeterministicFiniteAutomata() { }

    public DeterministicFiniteAutomata(DeterministicAutomataConverterBundle bundle) {
        this.alphabet = bundle.getAlphabet();
        this.stateTransitions = bundle.getStateTransitions();
        this.initialState = bundle.getInitialState();
        this.finalStates = bundle.getFinalStates();
        this.states = bundle.getStates();
    }

    public static DeterministicFiniteAutomata fromFile(String filePath) throws FileNotFoundException {
        DeterministicFiniteAutomata automata = new DeterministicFiniteAutomata();

        File inputFile = new File(filePath);
        Scanner fileReader = new Scanner(inputFile);

        String alphabetLine = fileReader.nextLine();
        automata.alphabet = Arrays.stream(alphabetLine.split(" "))
                .collect(toList());

        while (fileReader.hasNextLine()) {
            String nextLine = fileReader.nextLine();

            String[] lineArgs = nextLine.split(" ");

            State state = new State(lineArgs[0]);

            if (state.isFinal()) {
                automata.finalStates.add(state);
            } else if (state.isInitial()) {
                automata.initialState = state;
            }

            automata.states.add(state);


            for (int i = 0; i < automata.alphabet.size(); i++) {
                State toState = new State(lineArgs[i + 1]);
                String symbol = automata.alphabet.get(i);

                DeterministicStateTransition stateTransition = new DeterministicStateTransition(state, toState, symbol);
                automata.stateTransitions.add(stateTransition);
            }
        }


        return automata;
    }

    @Override
    public WordValidationResult check(String word) {
        List<String> characters = Arrays.stream(word.split("")).collect(toList());

        if (!alphabet.containsAll(characters)) {
            return WordValidationResult.Rejected;
        }

        State currentState = new State(this.initialState);

        while (!characters.isEmpty()) {
            String character = characters.remove(0);

            Optional<State> nextState = getNextState(currentState, character);

            if (nextState.isPresent()) {
                currentState = nextState.get();
            } else {
                return WordValidationResult.Blocked;
            }
        }

        if (finalStates.contains(currentState)) {
            return WordValidationResult.Accepted;
        } else {
            return WordValidationResult.Rejected;
        }
    }

    private Optional<State> getNextState(State currentState, String symbol) {
        return stateTransitions.stream()
                .filter(t -> currentState.equals(t.getFrom()) && symbol.equals(t.getSymbol()))
                .map(DeterministicStateTransition::getTo)
                .findFirst();
    }

    @Override
    public String toString() {
        String statesList = states.stream().map(Object::toString).collect(joining(", "));
        String finalStatesList = finalStates.stream().map(Object::toString).collect(joining(", "));
        String alphabetList = alphabet.stream().map(Object::toString).collect(joining(", "));

        return String.format("DFA = (states: {%s}, initial: %s, finals: {%s}, alphabet: {%s})", statesList, initialState, finalStatesList, alphabetList);
    }
}
