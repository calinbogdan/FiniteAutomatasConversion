package main.automata;

import java.util.HashSet;
import java.util.Set;

public class WordValidationTree {

    private Node rootNode;

    public WordValidationTree(State rootState) {
        rootNode = new Node(rootState);
    }

    public Node getRootNode() {
        return rootNode;
    }

    public class Node {
        private int level;
        private State state;
        private Set<Node> children = new HashSet<>();

        public Node(State state) {
            this.state = state;
        }

        public boolean addState(State state) {
            return children.add(new Node(state));
        }

        public Set<Node> getChildren() {
            return this.children;
        }

        public boolean hasChildren() {
            return this.children.size() > 0;
        }

        public State getState() {
            return state;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }
    }
}
