package main;

import main.automata.DeterministicFiniteAutomata;
import main.automata.NondeterministicFiniteAutomata;
import main.automata.WordValidationResult;
import main.automata.minimization.DeterministicAutomataMinimizer;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        DeterministicFiniteAutomata deterministicFiniteAutomata =
                DeterministicFiniteAutomata.fromFile("res/dfa_source.txt");

        NondeterministicFiniteAutomata nondeterministicFiniteAutomata =
                NondeterministicFiniteAutomata.fromFile("res/nfa_source.txt");

        System.out.println(nondeterministicFiniteAutomata);

        DeterministicFiniteAutomata dfa = nondeterministicFiniteAutomata.toDeterministicFiniteAutomata();

        DeterministicAutomataMinimizer minimizer = new DeterministicAutomataMinimizer(deterministicFiniteAutomata);
        DeterministicFiniteAutomata dfaMin = minimizer.minimize();
        dfaMin.writeToFile();

        System.out.println(dfa);
        System.out.println(dfaMin);

        Scanner wordScanner = new Scanner(System.in);

        while (true) {
            System.out.print("Cuvant: ");
            String word = wordScanner.nextLine();

            WordValidationResult result = dfa.check(word);

            System.out.println(String.format("Cuvantul este %s.", result));
        }
    }
}
