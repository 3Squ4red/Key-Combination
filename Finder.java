import java.util.ArrayList;
import java.util.Scanner;

import static java.util.Arrays.asList;

class Finder {

    static int[] indexes;
    public static void main(String[] args) {
        //Provide a key and combination
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a key (DO NOT ENTER A SPACE): ");
        String key = input.next();
        System.out.print("Enter a combination (This must contain all the letters in the key. Order doesn't matter): ");
        String combination = input.next();

        String filteredCombination = removeDuplicates(combination);
        indexes = new int[key.length()];

        System.out.print("\n(KEY: " + key + ")\t(PROVIDED COMBINATION: " + combination+")");
        System.out.println("\t(FILTERED COMBINATION: " + filteredCombination+")");

        FinderThread[] finderThreads = new FinderThread[key.length()];
        for (int i = 0; i < key.length(); i++)
            finderThreads[i] = FinderThread.createAndStart(key.charAt(i)+"#FinderThread", key.charAt(i)+"", filteredCombination, i);

        try {
            for (int i = 0; i < finderThreads.length; i++) {
                finderThreads[i].thread.join();
            }
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted");
        }
        System.out.println("\nHere's search summary");
        for (int i = 0; i < key.length(); i++)
            System.out.printf("%s(%d) : %d\n", key.charAt(i), i, indexes[i]);
    }

    static String removeDuplicates(String str) {
        if(str.equals("")) return "EMPTY STRING";
        String[] lArr = new String[str.length()];
        for (int i = 0; i < str.length(); i++) lArr[i] = str.charAt(i) + "";

        String newS;
        ArrayList<String> letters = new ArrayList<>(asList(lArr));

        int itr = 0;
        do {
            itr ++;
            for (int i = 0; i < letters.size() - 1; i++) {
                for (int j = i + 1; j < letters.size(); j++)
                    if (letters.get(i).equals(letters.get(j))) letters.remove(j);
            }

            newS = "";
            for (String letter : letters)
                newS += letter;
        } while (duplicatesArePresent(newS));

        System.out.printf("All duplicates were removed in %d iterations\n", itr);
        return newS;
    }

    static boolean duplicatesArePresent(String str) {
        for (int i = 0; i < str.length() - 1; i++) {
            for (int j = i + 1; j < str.length(); j++) {
                String iS = str.charAt(i) + "";
                String jS = str.charAt(j) + "";
                if (iS.equals(jS)) return true;
            }
        }
        return false;
    }
}