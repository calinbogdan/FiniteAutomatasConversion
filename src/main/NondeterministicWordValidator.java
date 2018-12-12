package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class NondeterministicWordValidator {

    private WordValidationTree tree;
    private List<String> charList;
    private NondeterministicFiniteAutomata automata;
    private int finishLevel = 0;
    private List<WordValidationTree.Node> finishLevelNodes = new ArrayList<>();

    NondeterministicWordValidator(State state, String word, NondeterministicFiniteAutomata automata) {
        tree = new WordValidationTree(state);
        charList = Arrays.stream(word.split("")).collect(Collectors.toList());
        this.automata = automata;
    }

    WordValidationResult check() {
        int currentLevel = 0;

        WordValidationTree.Node currentNode = tree.getRootNode();

        addChildrenForNode(currentNode, currentLevel);

        if (finishLevel == charList.size()) {
            if (finishLevelNodes.stream().anyMatch(n -> automata.getFinalStates().contains(n.getState()))) {
                return WordValidationResult.Accepted;
            }
        }

        return WordValidationResult.Rejected;
    }

    private void addChildrenForNode(WordValidationTree.Node currentNode, int currentLevel) {
        if (currentLevel < charList.size()) {
            List<State> nextStates = automata.getNextStates(currentNode.getState(), charList.get(currentLevel));
            nextStates.forEach(currentNode::addState);

            for (WordValidationTree.Node childNode : currentNode.getChildren()) {
                addChildrenForNode(childNode, currentLevel + 1);
            }

        } else {
            finishLevel = currentLevel;
            finishLevelNodes.add(currentNode);
        }
    }

}
