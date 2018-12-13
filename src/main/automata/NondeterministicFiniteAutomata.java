package main.automata;

import main.DeterministicFiniteAutomataConverter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class NondeterministicFiniteAutomata implements FiniteAutomata {

    private State initialState;
    private List<State> finalStates = new ArrayList<>();
    private List<String> alphabet = new ArrayList<>();
    private List<NondeterministicStateTransition> stateTransitions = new ArrayList<>();
    private List<State> states = new ArrayList<>();

    public static NondeterministicFiniteAutomata fromFile(String filePath) throws FileNotFoundException {
        NondeterministicFiniteAutomata automata = new NondeterministicFiniteAutomata();

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
                List<State> toStates = getToStatesList(lineArgs[i + 1]);
                String symbol = automata.alphabet.get(i);

                NondeterministicStateTransition transition = new NondeterministicStateTransition(state, toStates, symbol);
                automata.stateTransitions.add(transition);
            }
        }
        return automata;
    }

    private static List<State> getToStatesList(String lineArg) {
        if (lineArg.equals("#")) {
            return new ArrayList<>();
        } else {
            // get rid of '{' and '}'
            String commaSeparatedStates = lineArg.substring(1, lineArg.length() - 1);
            return Arrays.stream(commaSeparatedStates.split(","))
                    .map(State::new)
                    .collect(toList());
        }
    }

    @Override
    public String toString() {
        String statesList = states.stream().map(Object::toString).collect(joining(", "));
        String finalStatesList = finalStates.stream().map(Object::toString).collect(joining(", "));
        String alphabetList = alphabet.stream().map(Object::toString).collect(joining(", "));

        return String.format("NDFA = (states: {%s}, initial: %s, finals: {%s}, alphabet: {%s})", statesList, initialState, finalStatesList, alphabetList);
    }

    @Override
    public WordValidationResult check(String word) {

        NondeterministicWordValidator acceptor = new NondeterministicWordValidator(initialState, word, this);
        return acceptor.check();
    }

    private boolean stateTransitionMatches(NondeterministicStateTransition stateTransition, State state, String character) {
        boolean result = false;

        if (stateTransition.getFrom().equals(state)) {
            if (stateTransition.getSymbol().equals(character)) {
                result = true;
            }
        }

        return result;
    }

    List<State> getNextStates(State currentState, String character) {
        Optional<List<State>> nextStates = this.stateTransitions.stream()
                .filter(t -> stateTransitionMatches(t, currentState, character))
                .map(NondeterministicStateTransition::getTo)
                .findFirst();

        return nextStates.orElseGet(ArrayList::new);
    }

    public DeterministicFiniteAutomata toDeterministicFiniteAutomata() {
        DeterministicFiniteAutomataConverter converter = new DeterministicFiniteAutomataConverter(this);
        return converter.convert();
    }

    public State getInitialState() {
        return initialState;
    }

    public List<State> getFinalStates() {
        return finalStates;
    }

    public List<String> getAlphabet() {
        return alphabet;
    }

    public List<NondeterministicStateTransition> getStateTransitions() {
        return stateTransitions;
    }
}
