package main;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
       /* DeterministicFiniteAutomata deterministicFiniteAutomata =
                DeterministicFiniteAutomata.fromFile("res/dfa_source.txt"); */

        NondeterministicFiniteAutomata nondeterministicFiniteAutomata =
                NondeterministicFiniteAutomata.fromFile("res/nfa_source.txt");

        System.out.println(nondeterministicFiniteAutomata);

        DeterministicFiniteAutomata dfa = nondeterministicFiniteAutomata.toDeterministicFiniteAutomata();

        System.out.println(dfa);

        Scanner wordScanner = new Scanner(System.in);

        while (true) {
            System.out.print("Cuvant: ");
            String word = wordScanner.nextLine();

            WordValidationResult result = dfa.check(word);

            System.out.println(String.format("Cuvantul este %s.", result));
        }
    }
}
