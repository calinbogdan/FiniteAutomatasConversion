package main.automata.minimization;

import main.DeterministicAutomataLanguageBundle;
import main.automata.DeterministicFiniteAutomata;
import main.automata.DeterministicStateTransition;
import main.automata.State;

import java.util.*;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class DeterministicAutomataMinimizer {

    private EquivalenceList equivalenceList = new EquivalenceList();
    private final DeterministicFiniteAutomata automata;

    public DeterministicAutomataMinimizer(DeterministicFiniteAutomata automata) {
        this.automata = automata;
    }

    public DeterministicFiniteAutomata minimize() {

        Partition finalStatesPartition = new Partition();
        Partition nonFinalStatesPartition = new Partition();

        Set<State> nonFinalStates = automata.getStates().stream()
                .filter(not(State::isFinal))
                .collect(toSet());

        Set<State> finalStates = new HashSet<>(automata.getFinalStates());

        nonFinalStatesPartition.addAll(nonFinalStates);
        finalStatesPartition.addAll(finalStates);

        Equivalence zeroEquivalence = new Equivalence();
        zeroEquivalence.add(finalStatesPartition);
        zeroEquivalence.add(nonFinalStatesPartition);

        equivalenceList.add(zeroEquivalence);

        while (lastTwoEquivalencesAreDifferent()) {
            Equivalence lastEquivalence = equivalenceList.getLast();
            Equivalence newEquivalence = lastEquivalence.getCopyWithSinglePartitions();

            List<Partition> validPartitions = lastEquivalence.getPartitionsWithMoreThanOneElement();

            for (Partition validPartition : validPartitions) {
                Iterator<State> stateIterator = validPartition.iterator();

                State initialState = stateIterator.next();
                State nextState;

                while (stateIterator.hasNext()) {
                    nextState = stateIterator.next();
                    if (!stateIsEquivalentTo(initialState, nextState, validPartition)) {
                        newEquivalence.add(new Partition(nextState));
                        stateIterator.remove();
                    }
                }

                newEquivalence.add(validPartition);
            }

            equivalenceList.add(newEquivalence);
        }

        Equivalence equivalence = equivalenceList.getLast();

        List<State> states = equivalence.getPartitions().stream()
                .map(Partition::toState)
                .collect(toList());

        List<State> minimizedFinalStates = new ArrayList<>();


        DeterministicAutomataLanguageBundle bundle = new DeterministicAutomataLanguageBundle();

        for (State state : states) {
            if (state.contains(automata.getInitialState())) {
                state.setInitial(true);
                bundle.setInitialState(state);
            }

            if (automata.getFinalStates().stream().allMatch(state::contains)) {
                state.setFinal(true);
                minimizedFinalStates.add(state);
            }
        }

        bundle.setAlphabet(automata.getAlphabet());
        bundle.setStates(states);
        bundle.setFinalStates(minimizedFinalStates);


        List<DeterministicStateTransition> stateTransitions = new ArrayList<>();


        states.forEach(state -> {
           for (String character : automata.getAlphabet()) {

               // since there are states with more than one character, we can take only the first one, since the transition is the same

               State oneHourOfSleepState = new State(String.valueOf(state.toString().charAt(0)));
               State oldDelta = delta(oneHourOfSleepState, character);

               Optional<State> newDelta = states.stream()
                            .filter(s -> s.contains(oldDelta))
                            .findFirst();

               DeterministicStateTransition transition = new DeterministicStateTransition();
               transition.setFrom(state);
               transition.setSymbol(character);
               transition.setTo(newDelta.orElse(new State("F")));

               stateTransitions.add(transition);
           }
        });

        bundle.setStateTransitions(stateTransitions);

        return new DeterministicFiniteAutomata(bundle);
    }

    private boolean lastTwoEquivalencesAreDifferent() {
        if (equivalenceList.size() < 2) {
            return true;
        }

        Equivalence last = equivalenceList.get(equivalenceList.size() - 1);
        Equivalence beforeLast = equivalenceList.get(equivalenceList.size() - 2);
        return !last.equals(beforeLast);
    }

    private boolean stateIsEquivalentTo(State state1, State state2, Partition partition) {

        if (automata.getAlphabet().stream().allMatch(c -> delta(state1, c).equals(delta(state2, c)))) {
            return true;
        }

        // check that the different To-s are in the same partition
        return getTransitionsFor(state1, state2).stream()
                .map(DeterministicStateTransition::getTo)
                .allMatch(partition::contains);

    }

    private List<DeterministicStateTransition> getTransitionsFor(State state) {
        return this.automata.getStateTransitions().stream()
                .filter(t -> t.getFrom().equals(state))
                .collect(toList());
    }

    private List<DeterministicStateTransition> getTransitionsFor(State... states) {
        List<State> statesArr = Arrays.asList(states);

        return this.automata.getStateTransitions().stream()
                .filter(t -> statesArr.contains(t.getFrom()))
                .collect(toList());
    }

    private State delta(State currentState, String character) {
        return getTransitionsFor(currentState).stream()
                .filter(t -> t.getSymbol().equals(character))
                .map(DeterministicStateTransition::getTo)
                .findFirst().get();
    }
}
