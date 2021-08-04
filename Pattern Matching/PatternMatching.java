import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Your implementations of various pattern matchFounding algorithms.
 *
 * @author Rami Bouhafs
 * @version 1.0
 * @userid rbouhafs3
 * @GTID 903591700
 *
 * Collaborators: General help/tutoring of pattern matching algorithms by an upperclassman not in the class, Zach Minoh
 *
 * Resources: none
 */
public class PatternMatching {

    /**
     * Builds failure table that will be used to run the Knuth-Morris-Pratt
     * (KMP) algorithm.
     *
     * The table built should be the length of the input text.
     *
     * Note that a given index i will be the largest prefix of the pattern
     * indices [0..i] that is also a suffix of the pattern indices [1..i].
     * This means that index 0 of the returned table will always be equal to 0
     *
     * Ex. ababac
     *
     * table[0] = 0
     * table[1] = 0
     * table[2] = 1
     * table[3] = 2
     * table[4] = 3
     * table[5] = 0
     *
     * If the pattern is empty, return an empty array.
     *
     * @param pattern    a pattern you're building a failure table for
     * @param comparator you MUST use this for checking character equality
     * @return integer array holding your failure table
     * @throws java.lang.IllegalArgumentException if the pattern or comparator
     *                                            is null
     */
    public static int[] buildFailureTable(CharSequence pattern,
                                          CharacterComparator comparator) {
        if ((pattern == null) || (comparator == null)) {
            throw new IllegalArgumentException("The given pattern or comparator was null!!");
        } else {
            int patternLength = pattern.length();
            int[] failureTable = new int[patternLength];
            int i = 0;
            int j = 1;
            if (patternLength != 0) {
                failureTable[0] = 0;
            }
            while (j < patternLength) {
                if (comparator.compare(
                        pattern.charAt(i), pattern.charAt(j)) == 0) {
                    failureTable[j] = i + 1;
                    i++;
                    j++;
                } else if (pattern.charAt(i) != pattern.charAt(j) && i == 0) {
                    j++;
                } else {
                    i = failureTable[i - 1];
                }
            }

            return failureTable;
        }
    }


    /**
     * Knuth-Morris-Pratt (KMP) algorithm that relies on the failure table (also
     * called failure function). Works better with small alphabets.
     *
     * Make sure to implement the failure table before implementing this
     * method. The amount to shift by upon a mismatchFound will depend on this table.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each matchFound found
     * @throws java.lang.IllegalArgumentException if the pattern is null or of
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> kmp(CharSequence pattern, CharSequence text,
                                    CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("The given pattern was either null or had a length of zero!");
        }
        if ((text == null) || (comparator == null)) {
            throw new IllegalArgumentException("The given text or comparator was null!");
        } else {

            int i = 0;
            int j = 0;
            int textLength = text.length();
            int patternLength = pattern.length();

            List<Integer> returnH = new LinkedList<>();

            if (patternLength > textLength) {
                return returnH;
            }

            int[] failureTable = buildFailureTable(pattern, comparator);

            while (i - j - 1 < textLength - patternLength) {
                if (comparator.compare(
                        pattern.charAt(j), text.charAt(i)) == 0) {
                    if (j == patternLength - 1) {
                        returnH.add(i - (patternLength - 1));
                        j = failureTable[j];
                        i++;
                    } else {
                        i++;
                        j++;
                    }
                } else if (j == 0) {
                    i++;
                } else {
                    j = failureTable[j - 1];
                }
            }
            return returnH;
        }
    }

    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     *
     * Note that each char x will have an entry at table.get(x).
     * Each entry should be the last index of x, where x is a particular
     * character in the pattern.
     * If x is not in the pattern, then the table will not contain the key x,
     * and you will have to check for that in your Boyer Moore implementation.
     *
     * Ex. octocat
     *
     * table.get(o) = 3
     * table.get(c) = 4
     * table.get(t) = 6
     * table.get(a) = 5
     * table.get(everything else) = null, which you will interpret in
     * Boyer-Moore as -1
     *
     * If the pattern is empty, return an empty map.
     *
     * @param pattern a pattern you are building last table for
     * @return a Map with keys of all of the characters in the pattern mapping
     * to the index at which they last occurred in the pattern
     * @throws java.lang.IllegalArgumentException if the pattern is null
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("The given pattern was null!");
        } else {
            int patternLength = pattern.length();
            Map<Character, Integer> lastTable = new HashMap<>();
            for (int i = 0; i < patternLength; i++) {
                lastTable.put(pattern.charAt(i), i);
            }
            return lastTable;
        }
    }

    /**
     * Boyer Moore algorithm that relies on last occurrence table. Works better
     * with large alphabets.
     *
     * Note: You may find the getOrDefault() method useful from Java's Map.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each matchFound found
     * @throws java.lang.IllegalArgumentException if the pattern is null or of
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMoore(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("The given pattern was either null or had a length of 0!");
        }
        if ((text == null) || (comparator == null)) {
            throw new IllegalArgumentException("The given text or comparator was null!");
        } else {

            Map<Character, Integer> lastTable = buildLastTable(pattern);
            List<Integer> returnH = new LinkedList<>();
            int i = 0;
            int patternLength = pattern.length();
            int textLength = text.length();

            while (i <= (textLength - patternLength)) {
                int j = patternLength - 1;

                while (j >= 0 && comparator.compare(text.charAt(i + j), pattern.charAt(j)) == 0) {
                    j -= 1;
                }

                if (j == -1) { //found pattern
                    returnH.add(i);
                    i++;
                } else { // if pattern not found
                    int shift = lastTable.getOrDefault(text.charAt(i + j), -1);

                    if (shift < j) {
                        i += j - shift;
                    } else {
                        i++; //increment, then while loop repeats
                    }
                }
            }

            return returnH;
        }
    }

    /*
     * Prime base used for Rabin-Karp hashing.
     * DO NOT EDIT!
     */
    private static final int BASE = 113;

    /**
     * Runs the Rabin-Karp algorithm. This algorithms generates hashes for the
     * pattern and compares this hash to substrings of the text before doing
     * character by character comparisons.
     *
     * When the hashes are equal and you do character comparisons, compare
     * starting from the beginning of the pattern to the end, not from the end
     * to the beginning.
     *
     * You must use the Rabin-Karp Rolling Hash for this implementation. The
     * formula for it is:
     *
     * sum of: c * BASE ^ (pattern.length - 1 - i), where c is the integer
     * value of the current character, and i is the index of the character.
     * The BASE to use been provided for you in the final variable on line 127.
     *
     * Note that if you were dealing with very large numbers here, your hash
     * will likely overflow; you will not need to handle this case.
     * You may assume that all powers and calculations CAN be done without
     * overflow. However, be careful with how you carry out your calculations.
     * For example, if BASE^(m - 1) is a number that fits into an int, it's
     * possible for BASE^m will overflow. So, you would not want to do
     * BASE^m / BASE to find BASE^(m - 1).
     *
     * For example: Hashing "bunn" as a substring of "bunny" with base 113 hash
     * = b * 113 ^ 3 + u * 113 ^ 2 + n * 113 ^ 1 + n * 113 ^ 0 = 98 * 113 ^ 3 +
     * 117 * 113 ^ 2 + 110 * 113 ^ 1 + 110 * 113 ^ 0 = 142910419
     *
     * Another key step for this algorithm is that updating the hashcode from
     * one substring to the next one must be O(1). To update the hash:
     *
     * remove the oldChar times BASE raised to the length - 1, multiply by
     * BASE, and add the newChar.
     *
     * For example: Shifting from "bunn" to "unny" in "bunny" with base 113
     * hash("unny") = (hash("bunn") - b * 113 ^ 3) * 113 + y =
     * (142910419 - 98 * 113 ^ 3) * 113 + 121 = 170236090
     *
     * Keep in mind that calculating exponents is not O(1) in general, so you'll
     * need to keep track of what BASE^{m - 1} is for updating the hash.
     *
     * Do NOT use Math.pow() for this method.
     *
     * @param pattern    a string you're searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator the comparator to use when checking character equality
     * @return list containing the starting index for each matchFound found
     * @throws java.lang.IllegalArgumentException if the pattern is null or of
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> rabinKarp(CharSequence pattern,
                                          CharSequence text,
                                          CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("The given pattern was null or had a length of 0!");
        }
        if ((text == null) || (comparator == null)) {
            throw new IllegalArgumentException("The given text or comparator was null!");
        } else {

            List<Integer> returnH = new LinkedList<>();

            int i = 0;
            int textLength = text.length();
            int patternLength = pattern.length();
            int givenBase = 1;

            if (patternLength > textLength) {
                return returnH;
            }

            int patHash = pattern.charAt(patternLength - 1);
            int textHash = text.charAt(patternLength - 1);


            for (int k = patternLength - 2; k >= 0; k--) {
                givenBase *= BASE;
                patHash += pattern.charAt(k) * givenBase;
                textHash += text.charAt(k) * givenBase;
            }

            while (i - 1 < textLength - patternLength) {
                if (patHash == textHash) {

                    boolean matchFound = true;
                    for (int j = 0; j < patternLength; j++) {
                        if (comparator.compare(pattern.charAt(j), text.charAt(i + j)) != 0) {
                            matchFound = false;
                            break;
                        }
                    }

                    if (matchFound) {
                        returnH.add(i);
                    }
                }
                if (i + patternLength >= textLength) {
                    break;
                }

                textHash = (textHash - (text.charAt(i) * givenBase)) * BASE
                        + text.charAt(i + patternLength);
                i++;
            }

            return returnH;
        }
    }
}