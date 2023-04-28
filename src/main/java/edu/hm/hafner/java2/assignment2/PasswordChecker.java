package edu.hm.hafner.java2.assignment2;

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
        this.dictionary = dictionary;
    }

    /**
     * Returns whether the password is strong enough, or not.
     *
     * @param password
     *         the password to check
     *
     * @return {@code true} if the password is strong enough, {@code false} otherwise
     */
    @SuppressWarnings("checkstyle:SimplifyBooleanReturn")
    public boolean isValid(final String password) {
        if (!isTwentyCharsLong(password)) {
            return false;
        }
        else if (!containsLowercaseCharacter(password)) {
            return false;
        }
        else if (!containsUppercaseCharacter(password)) {
            return false;
        }
        else if (!containsDigit(password)) {
            return false;
        }
        else if (!containsSpecialCharacter(password)) {
            return false;
        }
        else if (!hasTenDifferentCharacters(password)) {
            return false;
        }
        else if (!onlyContainsAscii(password)) {
            return false;
        }
        else if (isSimilarToDictionary(password)) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Checks if string has more than 20 characters.
     *
     * @param string
     *         string to check
     *
     * @return if string has more than 20 characters
     */
    private boolean isTwentyCharsLong(final String string) {
        return string.length() >= 20;
    }

    /**
     * Checks if string contains at least 1 lowercase letter.
     *
     * @param string
     *         string to check
     *
     * @return if string contains lowercase letter
     */
    private boolean containsLowercaseCharacter(final String string) {
        for (char character : string.toCharArray()) {
            if (Character.isLowerCase(character)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if string contains at least 1 uppercase letter.
     *
     * @param string
     *         string to check
     *
     * @return if string contains uppercase letter
     */
    private boolean containsUppercaseCharacter(final String string) {
        for (char character : string.toCharArray()) {
            if (Character.isUpperCase(character)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if string contains at least 1 digit.
     *
     * @param string
     *         string to check
     *
     * @return if string contains digit
     */
    private boolean containsDigit(final String string) {
        for (char character : string.toCharArray()) {
            if (Character.isDigit(character)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if string contains at least one of the following special characters: +, -, !, %, $, #.
     *
     * @param string
     *         string to check
     *
     * @return if string contains special character
     */
    private boolean containsSpecialCharacter(final String string) {
        String[] specialCharacters = new String[] {"+", "-", "!", "%", "$", "#"};
        for (String specialChar : specialCharacters) {
            if (string.contains(specialChar)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a given string consists of at least 10 different characters.
     *
     * @param string
     *         string to check
     *
     * @return if string has 10 different characters
     */
    private boolean hasTenDifferentCharacters(final String string) {
        StringBuilder usedLetters = new StringBuilder();
        for (char character : string.toCharArray()) {
            if (!usedLetters.toString().contains(String.valueOf(character))) {
                usedLetters.append(character);
            }
        }
        return usedLetters.length() >= 10;
    }

    /**
     * Checks if a given string only contains characters present in base ascii standard (32 - 126).
     *
     * @param string
     *         string to check
     *
     * @return if string only contains ascii
     */
    private boolean onlyContainsAscii(final String string) {
        for (char character : string.toCharArray()) {
            int charCode = character;
            if (charCode < 32 || charCode > 126) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if string is similar to the dictionary of the PasswordChecker.
     *
     * @param string
     *         string to check
     *
     * @return is string is similar
     */
    private boolean isSimilarToDictionary(final String string) {
        for (String dictString : dictionary) {
            if (dictString.equalsIgnoreCase(string)) {
                return true;
            }
            if (isSameStringWithOneLessCharacter(dictString, string)) {
                return true;
            }
            if (isSameStringWithOneLessCharacter(string, dictString)) {
                return true;
            }
            if (isSameStringWithOneCharVariation(dictString, string)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if 2 strings are equal under the condition, that string b has only 1 more character at any position.
     *
     * @param a
     *         base string
     * @param b
     *         string to compare against base string
     *
     * @return if 2 strings are equal except for one different character
     */
    private boolean isSameStringWithOneLessCharacter(final String a, final String b) {
        // remove one character from string and check for equality
        for (int i = 0; i < b.length(); i++) {
            // remove character at index i from string
            StringBuilder stringBuilder = new StringBuilder(b);
            String reducedString = stringBuilder.deleteCharAt(i).toString();
            if (a.equals(reducedString)) {
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
