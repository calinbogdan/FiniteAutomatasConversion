package main.automata.minimization;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Equivalence {

    private List<Partition> partitions = new ArrayList<>();

    public void add(Partition partition) {
        partitions.add(new Partition(partition));
    }

    public int size() {
        return partitions.size();
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof Equivalence) {
            Equivalence eq = (Equivalence) obj;
            return eq.size() == size();
        }

        return false;
    }

    public List<Partition> getPartitionsWithMoreThanOneElement() {
        return new ArrayList<>(partitions.stream().filter(p -> p.size() > 1).collect(toList()));
    }

    private List<Partition> getSinglePartitions() {
        List<Partition> singlePartitions = partitions.stream()
                .filter(p -> p.size() == 1)
                .collect(toList());
        return new ArrayList<>(singlePartitions);
    }

    public Equivalence getCopyWithSinglePartitions() {
        Equivalence equivalence = new Equivalence();
        equivalence.partitions = getSinglePartitions();
        return equivalence;
    }

    public List<Partition> getPartitions() {
        return partitions;
    }
}


