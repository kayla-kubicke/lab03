package adventure;

import common.IntList;
import edu.princeton.cs.algs4.In;

import java.util.Map;
import java.util.TreeMap;

public class PalindromeStage implements AdventureStage {

    private final In in;
    private final Map<String, AdventureStage> responses;

    public PalindromeStage(In in) {
        this.in = in;
        AdventureStage nextStage = new FillerStage(
                "Mm, tasty. Briefly, you wonder if free will is an illusion. You hear some people talking " +
                        "about a machine in Soda and decide to check it out.",
                Map.of("go", new MachineStage(in))
        );
        this.responses = new TreeMap<>(Map.of(
                "va cafe", nextStage,
                "hearst food court", nextStage
        ));
    }

    @Override
    public void playStage() {
        System.out.println("""
                The Woz is a cool name, much better than "Soda 430". Soda 606 is a neat, symmetric conference room,
                like its number. We could rename The Woz to be palindromic, but it sounds so cool! Why not change the
                room number instead?
                (Give a palindromic room number.)
                """);
        while (true) {
            String input = in.readLine();
            while (!AdventureUtils.isInt(input)) {
                System.out.println("Please enter a valid integer.");
                input = this.in.readLine();
            }

            IntList numLst = digitsToIntList(input);
            // Is reverseList(...) working correctly? No.
            // https://www.youtube.com/watch?v=Gdyc7BpKic0
            IntList reversedLst = reverseList(numLst);
            // reversedLst = reversedLst.of(4, 0, 4); // Works.

            if (numLst.equals(reversedLst)) {
                System.out.println("Wow, nice room number!");
                break;
            }

            System.out.println("That's not a palindrome! Try again.");
        }
    }

    @Override
    public String nextStagePrompt() {
        return "Hmm, you're getting hungry. Where do you want to go?";
    }

    @Override
    public Map<String, AdventureStage> getResponses() {
        return responses;
    }

    /** Returns a new IntList with the contents of the original IntList in reverse order.*/
    private static IntList reverseList(IntList l) {
        IntList reversed = null;
        // Does not add the last number because of the condition statement.
        // As to say, the last node is ignored because the remaining list is null so
        // the loop terminates.
        while (l.rest != null) {
            reversed = new IntList(l.first, reversed);
            l = l.rest;
        }

        return reversed;
    }

    /**
     * Given an input string of digits, converts it into an IntList of single-digit ints.
     * For example, the string "606" is converted to 6 -> 0 -> 6.
     */
    private static IntList digitsToIntList(String s) {
        int[] a = new int[s.length()];
        // i needed to be decremented to avoid an infinite loop; as to say, the condition would
        // never be met.
        for (int i = s.length(); i > 0; i--) {
            // Replaced i with currentIndex because, even with the fix above, the first
            // iteration of the loop would cause a StringIndexOutOfBoundsException
            // because the given string's length will always be one greater than the
            // last index on a zero-indexed string.
            int currentIndex = s.length() - i;
            a[s.length() - i] = Character.getNumericValue(s.charAt(currentIndex));
        }
        return IntList.of(a);
    }

}
