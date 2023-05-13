package edu.hm.hafner.java2.assignment2;

import java.util.Arrays;

/**
 * Ensures that a password is strong enough.
 */
public class PasswordChecker {
    private final String[] dictionary;

    /**
     * Creates a new {@link PasswordChecker} instance.
     *
     * @param dictionary
     *         a dictionary of not allowed passwords
     */
    public PasswordChecker(final String... dictionary) {
        this.dictionary = Arrays.copyOf(dictionary, dictionary.length);
    }

    /**
     * Returns whether the password is strong enough, or not.
     *
     * @param password
     *         the password to check
     *
     * @return {@code true} if the password is strong enough, {@code false} otherwise
     */
    public boolean isValid(final String password) {
        if (!isLongEnough(password)) {
            return false;
        }
        if (!containsLowercaseCharacter(password)) {
            return false;
        }
        if (!containsUppercaseCharacter(password)) {
            return false;
        }
        if (!containsDigit(password)) {
            return false;
        }
        if (!containsSpecialCharacter(password)) {
            return false;
        }
        if (!hasTenDifferentCharacters(password)) {
            return false;
        }
        if (!onlyContainsAscii(password)) {
            return false;
        }
        return !isSimilarToDictionary(password);
    }

    /**
     * Checks if the password string has at least 20 characters.
     *
     * @param pwd
     *         string to check
     *
     * @return {@code true} if the password string has at least 20 characters
     */
    private boolean isLongEnough(final String pwd) {
        return pwd.length() >= 20;
    }

    /**
     * Checks if the password string contains at least 1 lowercase letter.
     *
     * @param pwd
     *         string to check
     *
     * @return {@code true} if the password string contains at least 1 lowercase letter
     */
    private boolean containsLowercaseCharacter(final String pwd) {
        for (int i = 0; i < pwd.length(); i++) {
            if (Character.isLowerCase(pwd.charAt(i))) {
                return true;
            }
        }
        return false;
        // Tricky alternative: pwd.toUpperCase(Locale.ENGLISH).equals(password)
    }

    /**
     * Checks if the password string contains at least 1 uppercase letter.
     *
     * @param pwd
     *         string to check
     *
     * @return {@code true} if the password string contains at least 1 uppercase letter
     */
    private boolean containsUppercaseCharacter(final String pwd) {
        for (int i = 0; i < pwd.length(); i++) {
            if (Character.isUpperCase(pwd.charAt(i))) {
                return true;
            }
        }
        return false;
        // Tricky alternative: pwd.toLowerCase(Locale.ENGLISH).equals(password)
    }

    /**
     * Checks if the password string contains at least 1 digit.
     *
     * @param pwd
     *         string to check
     *
     * @return {@code true} if the password string contains digit
     */
    private boolean containsDigit(final String pwd) {
        for (int i = 0; i < pwd.length(); i++) {
            if (Character.isDigit(pwd.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the password string contains at least one of the following special characters: +, -, !, %, $, #.
     *
     * @param pwd
     *         string to check
     *
     * @return {@code true} if the password string contains special character
     */
    private boolean containsSpecialCharacter(final String pwd) {
        String[] specialCharacters = {"+", "-", "!", "%", "$", "#"};
        for (String specialCharacter : specialCharacters) {
            if (pwd.contains(specialCharacter)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a given string consists of at least 10 different characters.
     *
     * @param pwd
     *         string to check
     *
     * @return {@code true} if the password string has 10 different characters
     */
    private boolean hasTenDifferentCharacters(final String pwd) {
        StringBuilder usedLetters = new StringBuilder();
        for (int i = 0; i < pwd.length(); i++) {
            if (usedLetters.toString().indexOf(pwd.charAt(i)) == -1) {
                usedLetters.append(pwd.charAt(i));
            }
        }
        return usedLetters.length() >= 10;
    }

    /**
     * Checks if a given string only contains characters present in base ascii standard (32 - 126).
     *
     * @param pwd
     *         string to check
     *
     * @return {@code true} if the password string only contains ascii
     */
    private boolean onlyContainsAscii(final String pwd) {
        char maxAscii = 127;
        for (int i = 0; i < pwd.length(); i++) {
            if (pwd.charAt(i) > maxAscii) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the password string is similar to the dictionary of the PasswordChecker.
     *
     * @param pwd
     *         string to check
     *
     * @return is string is similar
     */
    private boolean  isSimilarToDictionary(final String pwd) {
        for (String dictString : dictionary) {
            if (dictString.equalsIgnoreCase(pwd)) {
                return true;
            }
            if (isSameStringWithOneLessCharacter(dictString, pwd)) {
                return true;
            }
            if (isSameStringWithOneLessCharacter(pwd, dictString)) {
                return true;
            }
            if (isSameStringWithOneCharVariation(dictString, pwd)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if 2 strings are equal under the condition, that string right has only 1 more character at any position.
     *
     * @param left
     *         base string
     * @param right
     *         string to compare against base string
     *
     * @return if 2 strings are equal except for one different character
     */
    private boolean isSameStringWithOneLessCharacter(final String left, final String right) {
        // remove one character from string and check for equality
        for (int i = 0; i < right.length(); i++) {
            // remove character at index i from string
            StringBuilder stringBuilder = new StringBuilder(right);
            String reducedString = stringBuilder.deleteCharAt(i).toString();
            if (left.equals(reducedString)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if 2 strings are equal under the condition, that only one character is different.
     *
     * @param a
     *         first string
     * @param b
     *         second string
     *
     * @return if strings are equal with one character variation
     */
    private boolean isSameStringWithOneCharVariation(final String a, final String b) {
        if (a.length() != b.length()) {
            return false;
        }

        for (int i = 0; i < a.length(); i++) {
            // remove character at index i from both strings and check for equality
            String aReduced = a.substring(0, i) + a.substring(i + 1);
            String bReduced = b.substring(0, i) + b.substring(i + 1);
            if (aReduced.equals(bReduced)) {
                return true;
            }
        }
        return false;
    }
}
