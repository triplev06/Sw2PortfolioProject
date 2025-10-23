import javax.sound.midi.Sequence;
/**
 * Enhanced {@code VigenereCipherKernel} with secondary methods.
 *
 * <p>This interface adds a few helper operations for convenience,
 * such as setting the key from a {@code String}, converting between
 * {@code Sequence<Character>} and {@code String}, and checking if
 * the stored key is valid.</p>
 *
 * @author Vikranth Vegesina
 */
public interface VigenereCipher
    extends Comparable<VigenereCipher>, VigenereCipherKernel {


    /**
     * Encrypts the given text using the provided key.
     * This method does not change the stored key.
     *
     * @param text
     *            the text to encrypt
     * @param key
     *            the key to use
     * @return the ciphertext produced
     * @requires text != null and key != null and key.length() > 0
     *           and every character of key is a letter
     * @ensures encryptWithKey =
     *          TEXT_ENCRYPTED_WITH_KEY(text, TO_UPPERCASE(key))
     */
    Sequence<Character> encryptWithKey(Sequence<Character> text, Sequence<Character> key);

    /**
     * Decrypts the given ciphertext using the provided key.
     * This method does not change the stored key.
     *
     * @param text
     *            the ciphertext to decrypt
     * @param key
     *            the key to use
     * @return the text produced
     * @requires text != null and key != null and key.length() > 0
     *           and every character of key is a letter
     * @ensures decryptWithKey =
     *          TEXT_DECRYPTED_WITH_KEY(text, TO_UPPERCASE(key))
     */
    Sequence<Character> decryptWithKey(Sequence<Character> text, Sequence<Character> key);

    /**
     * Checks whether the stored key is valid.
     * A valid key is non-empty and made up only of letters.
     *
     * @return true if the key is valid, false otherwise
     * @ensures isValidKey =
     *          (this.key.length() > 0 and every element of this.key is a letter)
     */
    boolean isValidKey();

    /**
     * Sets the key from the given {@code String}.
     * Only letters are allowed.
     *
     * @param s
     *            the new key as a string
     * @replaces this
     * @requires s != null and s.length() > 0 and
     *           every character of s is a letter
     * @ensures this.key = TO_UPPERCASE(CHAR_SEQUENCE_OF(s))
     */
    void setKeyFromString(String s);

    /**
     * Returns the stored key as a {@code String}.
     *
     * @return the key as a string
     * @ensures keyToString = TO_STRING(this.key)
     */
    String keyToString();

    /**
     * Converts the given {@code String} to a {@code Sequence<Character>}.
     *
     * @param s
     *            the string to convert
     * @return the resulting sequence of characters
     * @requires s != null
     * @ensures stringToSequence = CHAR_SEQUENCE_OF(s)
     */
    Sequence<Character> stringToSequence(String s);

    /**
     * Converts the given {@code Sequence<Character>} to a {@code String}.
     *
     * @param seq
     *            the sequence to convert
     * @return the resulting string
     * @requires seq != null
     * @ensures sequenceToString = TO_STRING(seq)
     */
    String sequenceToString(Sequence<Character> seq);
}
