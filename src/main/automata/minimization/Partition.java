package main.automata.minimization;

import main.automata.State;

import java.util.*;

import static java.util.stream.Collectors.joining;

public class Partition implements Set<State> {

    private Set<State> states = new HashSet<>();

    public Partition() { }

    public Partition(State state) {
        states.add(state);
    }

    public Partition(Partition partition) {
        states = new HashSet<>(partition.states);
    }

    public State toState() {
        String stateSymbol = states.stream().map(Objects::toString).collect(joining());
        return new State(stateSymbol);
    }

    @Override
    public int size() {
        return states.size();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        return states.contains(o);
    }

    @Override
    public Iterator<State> iterator() {
        return states.iterator();
    }

    @Override
    public Object[] toArray() {
        return states.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return states.toArray(a);
    }

    @Override
    public boolean add(State state) {
        return states.add(state);
    }

    @Override
    public boolean remove(Object o) {
        return states.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return states.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends State> c) {
        return states.addAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return states.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return states.removeAll(c);
    }

    @Override
    public void clear() {
        states.clear();
    }
}
