package main.automata.minimization;

import java.util.*;

public class EquivalenceList implements List<Equivalence> {

    private List<Equivalence> equivalences = new ArrayList<>();

    public Equivalence getLast() {
        return equivalences.get(size() - 1);
    }

    @Override
    public int size() {
        return equivalences.size();
    }

    @Override
    public boolean isEmpty() {
        return equivalences.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return equivalences.contains(o);
    }

    @Override
    public Iterator<Equivalence> iterator() {
        return equivalences.iterator();
    }

    @Override
    public Object[] toArray() {
        return equivalences.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return equivalences.toArray(a);
    }

    @Override
    public boolean add(Equivalence equivalence) {
        return equivalences.add(equivalence);
    }

    @Override
    public boolean remove(Object o) {
        return equivalences.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return equivalences.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends Equivalence> c) {
        return equivalences.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends Equivalence> c) {
        return equivalences.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return equivalences.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return equivalences.retainAll(c);
    }

    @Override
    public void clear() {
        equivalences.clear();
    }

    @Override
    public Equivalence get(int index) {
        return equivalences.get(index);
    }

    @Override
    public Equivalence set(int index, Equivalence element) {
        return equivalences.set(index, element);
    }

    @Override
    public void add(int index, Equivalence element) {
        equivalences.add(index, element);
    }

    @Override
    public Equivalence remove(int index) {
        return equivalences.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return equivalences.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return equivalences.lastIndexOf(o);
    }

    @Override
    public ListIterator<Equivalence> listIterator() {
        return equivalences.listIterator();
    }

    @Override
    public ListIterator<Equivalence> listIterator(int index) {
        return equivalences.listIterator(index);
    }

    @Override
    public List<Equivalence> subList(int fromIndex, int toIndex) {
        return equivalences.subList(fromIndex, toIndex);
    }
}
