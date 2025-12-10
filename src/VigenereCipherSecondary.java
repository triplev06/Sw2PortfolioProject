import components.sequence.Sequence;
import components.sequence.Sequence1L;

/**
 * Layered implementations of secondary methods for {@code VigenereCipher}.
 *
 * <p>
 * All secondary methods are implemented using only kernel methods and other
 * secondary methods. Kernel methods remain abstract.
 * </p>
 *
 * @author Vikranth Vegesina
 */
public abstract class VigenereCipherSecondary implements VigenereCipher {

    /*
     * Private members
     */

    /**
     * Alphabet used for Vigenere cipher operations.
     */
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /*
     * Private helper methods
     */

    /**
     * Converts a character to uppercase if it is a letter.
     *
     * @param c
     *            the character to convert
     * @return the uppercase version if letter, otherwise the character itself
     */
    private static char toUpperCase(char c) {
        if (c >= 'a' && c <= 'z') {
            return (char) (c - 'a' + 'A');
        }
        return c;
    }

    /**
     * Checks if a character is a letter.
     *
     * @param c
     *            the character to check
     * @return true if the character is a letter, false otherwise
     */
    private static boolean isLetter(char c) {
        return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
    }

    /**
     * Encrypts or decrypts a single character using the Vigenere cipher.
     *
     * @param ch
     *            the character to process
     * @param keyChar
     *            the key character to use
     * @param encrypt
     *            true to encrypt, false to decrypt
     * @return the processed character
     */
    private static char processChar(char ch, char keyChar, boolean encrypt) {
        if (!isLetter(ch)) {
            return ch;
        }

        char upperCh = toUpperCase(ch);
        char upperKey = toUpperCase(keyChar);

        int charIndex = ALPHABET.indexOf(upperCh);
        int keyIndex = ALPHABET.indexOf(upperKey);

        int resultIndex;
        if (encrypt) {
            resultIndex = (charIndex + keyIndex) % 26;
        } else {
            resultIndex = (charIndex - keyIndex + 26) % 26;
        }

        return ALPHABET.charAt(resultIndex);
    }

    /*
     * Public members
     */

    /*
     * Secondary methods - implemented using only kernel methods
     */

    @Override
    public final Sequence<Character> encryptWithKey(Sequence<Character> text,
            Sequence<Character> key) {
        assert text != null : "Violation of: text is not null";
        assert key != null : "Violation of: key is not null";
        assert key.length() > 0 : "Violation of: |key| > 0";

        // Save the current key
        Sequence<Character> savedKey = this.key();

        // Temporarily set the new key
        Sequence<Character> tempKey = key.newInstance();
        tempKey.append(key);
        this.setKey(tempKey);

        // Encrypt with the temporary key
        Sequence<Character> result = this.encrypt(text);

        // Restore the original key
        this.setKey(savedKey);

        return result;
    }

    @Override
    public final Sequence<Character> decryptWithKey(Sequence<Character> text,
            Sequence<Character> key) {
        assert text != null : "Violation of: text is not null";
        assert key != null : "Violation of: key is not null";
        assert key.length() > 0 : "Violation of: |key| > 0";

        // Save the current key
        Sequence<Character> savedKey = this.key();

        // Temporarily set the new key
        Sequence<Character> tempKey = key.newInstance();
        tempKey.append(key);
        this.setKey(tempKey);

        // Decrypt with the temporary key
        Sequence<Character> result = this.decrypt(text);

        // Restore the original key
        this.setKey(savedKey);

        return result;
    }

    @Override
    public final boolean isValidKey() {
        Sequence<Character> currentKey = this.key();
        boolean valid = currentKey.length() > 0;

        if (valid) {
            for (int i = 0; i < currentKey.length(); i++) {
                if (!isLetter(currentKey.entry(i))) {
                    valid = false;
                    break;
                }
            }
        }

        return valid;
    }

    @Override
    public final void setKeyFromString(String s) {
        assert s != null : "Violation of: s is not null";
        assert s.length() > 0 : "Violation of: |s| > 0";

        Sequence<Character> newKey = this.stringToSequence(s);
        this.setKey(newKey);
    }

    @Override
    public final String keyToString() {
        Sequence<Character> currentKey = this.key();
        return this.sequenceToString(currentKey);
    }

    @Override
    public final Sequence<Character> stringToSequence(String s) {
        assert s != null : "Violation of: s is not null";

        Sequence<Character> result = new Sequence1L<>();
        for (int i = 0; i < s.length(); i++) {
            result.add(result.length(), s.charAt(i));
        }
        return result;
    }

    @Override
    public final String sequenceToString(Sequence<Character> seq) {
        assert seq != null : "Violation of: seq is not null";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < seq.length(); i++) {
            sb.append(seq.entry(i));
        }
        return sb.toString();
    }

}