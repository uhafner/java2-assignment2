package edu.hm.hafner.java2.assignment2;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Testet die Klasse {@link PasswordChecker}.
 *
 * @author Ullrich Hafner
 */
@Disabled("Disabled until the implementation is available")
class PasswordCheckerTest {
    private static final String VALID = "qwertyuiopasdfghjA0!";

    @Test
    void shouldRejectCheckEmptyPassword() {
        assertThat(new PasswordChecker().isValid("")).isFalse();
        assertThat(new PasswordChecker("").isValid("")).isFalse();
    }

    @Test
    void shouldDetectValidPassword() {
        assertThat(new PasswordChecker().isValid(VALID)).isTrue();
    }

    @Test
    void shouldRejectPasswordWithoutUpperCase() {
        assertThat(new PasswordChecker().isValid("qwertyuiopasdfghja0!")).isFalse();
    }

    @Test
    void shouldRejectPasswordWithoutLowerCase() {
        assertThat(new PasswordChecker().isValid("QWERTYUIOPASDFGHJA0!")).isFalse();
    }

    @Test
    void shouldRejectPasswordWithoutDigit() {
        assertThat(new PasswordChecker().isValid("qwertyuiopasdfghjAB!")).isFalse();
    }

    @Test
    void shouldRejectPasswordWithoutSpecialCharacter() {
        assertThat(new PasswordChecker().isValid("qwertyuiopasdfghjA01")).isFalse();
        assertThat(new PasswordChecker().isValid("qwertyuiopasdfghjA0/")).isFalse();
    }

    @Test
    void shouldRejectPasswordOutsideAscii() {
        assertThat(new PasswordChecker().isValid("qwertyuiopasdfghßA0!")).isFalse();
    }

    @Test
    void shouldRejectPasswordWithNoVariation() {
        assertThat(new PasswordChecker().isValid("aaaaaaaaaBBBBBBBBBB0!")).isFalse();
    }

    @Test
    void shouldRejectPasswordFromDictionary() {
        assertThat(new PasswordChecker(VALID).isValid(VALID)).isFalse();
        assertThat(new PasswordChecker("something", VALID).isValid(VALID)).isFalse();
        assertThat(new PasswordChecker("something", VALID, "end").isValid(VALID)).isFalse();

        assertThat(new PasswordChecker("qwertyuiopasdfghjA0!").isValid("qwertyuiopasdfghjA-0!")).isFalse();
        assertThat(new PasswordChecker("qwertyuiopasdfghjA0!").isValid("qwertyuiopasdfghjA--0!")).isTrue();

        assertThat(new PasswordChecker("qwertyuiopasdfghjA0!01").isValid("qwertyuiopasdfghjA0!01")).isFalse();
        assertThat(new PasswordChecker("qwertyuiopasdfghjA0!01").isValid("qwertyuiopasdfghjA0!0")).isFalse();
        assertThat(new PasswordChecker("qwertyuiopasdfghjA0!01").isValid("qwertyuiopasdfghjA0!")).isTrue();

        assertThat(new PasswordChecker("qwertyuiopasdfghjA0!").isValid("qwertyuiopasdfghjA0!")).isFalse();
        assertThat(new PasswordChecker("qwertyuiopasdfghjA0!").isValid("qwertyuiopasdAghjA0!")).isFalse();
        assertThat(new PasswordChecker("qwertyuiopasdfghjA0!").isValid("qwertyuiopasAAghjA0!")).isTrue();
    }

    @Test
    void shouldDetectSimilarWordsSameLength() {
        detectDictionaryMatch("pale", "PALE", false);
        detectDictionaryMatch("pale", "pale", false);
        detectDictionaryMatch("pAle", "paLe", false);
    }

    @Test
    void shouldDetectSimilarWordsSmallerOrLarger() {
        detectDictionaryMatch("pale", "pal", false);
        detectDictionaryMatch("pale", "ale", false);
        detectDictionaryMatch("pale", "pae", false);

        detectDictionaryMatch("pal", "pale", false);
        detectDictionaryMatch("ale", "pale", false);
        detectDictionaryMatch("pae", "pale", false);

        detectDictionaryMatch("pale", "paf", true);
        detectDictionaryMatch("pale", "aal", true);
        detectDictionaryMatch("pale", "pbl", true);
    }

    @Test
    void shouldDetectFlipped() {
        detectDictionaryMatch("pale", "pale", false);
        detectDictionaryMatch("pale", "dale", false);
        detectDictionaryMatch("pale", "pble", false);
        detectDictionaryMatch("pale", "palf", false);
        detectDictionaryMatch("pale", "paee", false);

        detectDictionaryMatch("pale", "aple", true);
        detectDictionaryMatch("pale", "plae", true);
        detectDictionaryMatch("pale", "pael", true);
        detectDictionaryMatch("pale", "ealp", true);
    }

    private void detectDictionaryMatch(final String dictionary, final String password, final boolean expectedIsValid) {
        String message = String.format("Password %s should be detected as valid=%s but was not (dictionary = %s)",
                password, expectedIsValid, dictionary);
        assertThat(new PasswordChecker(VALID + dictionary).isValid(VALID + password))
                .as(message)
                .isEqualTo(expectedIsValid);
        assertThat(new PasswordChecker(dictionary + VALID).isValid(password + VALID))
                .as(message)
                .isEqualTo(expectedIsValid);
    }
}
