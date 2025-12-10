import components.sequence.Sequence;

/**
 * Enhanced {@code VigenereCipherKernel} with secondary methods.
 *
 * <p>
 * This interface adds convenience operations such as encrypting/decrypting with
 * a temporary key, converting between {@code Sequence<Character>} and
 * {@code String}, and validating keys.
 * </p>
 *
 * @author Vikranth Vegesina
 */
public interface VigenereCipher extends VigenereCipherKernel {

    /**
     * Encrypts the given text using the provided key without changing the
     * stored key.
     *
     * @param text
     *            the text to encrypt
     * @param key
     *            the key to use for encryption
     * @return the ciphertext produced
     * @requires text /= null and key /= null and |key| > 0 and every character
     *           of key is a letter
     * @ensures encryptWithKey = TEXT_ENCRYPTED_WITH_KEY(text,
     *          TO_UPPERCASE(key))
     */
    Sequence<Character> encryptWithKey(Sequence<Character> text,
            Sequence<Character> key);

    /**
     * Decrypts the given ciphertext using the provided key without changing the
     * stored key.
     *
     * @param text
     *            the ciphertext to decrypt
     * @param key
     *            the key to use for decryption
     * @return the plaintext produced
     * @requires text /= null and key /= null and |key| > 0 and every character
     *           of key is a letter
     * @ensures decryptWithKey = TEXT_DECRYPTED_WITH_KEY(text,
     *          TO_UPPERCASE(key))
     */
    Sequence<Character> decryptWithKey(Sequence<Character> text,
            Sequence<Character> key);

    /**
     * Checks whether the stored key is valid. A valid key is non-empty and
     * consists only of letters.
     *
     * @return true if the key is valid, false otherwise
     * @ensures isValidKey = (|this.key| > 0 and every element of this.key is a
     *          letter)
     */
    boolean isValidKey();

    /**
     * Sets the key from the given {@code String}.
     *
     * @param s
     *            the new key as a string
     * @replaces this.key
     * @requires s /= null and |s| > 0 and every character of s is a letter
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
     * @requires s /= null
     * @ensures stringToSequence = CHAR_SEQUENCE_OF(s)
     */
    Sequence<Character> stringToSequence(String s);

    /**
     * Converts the given {@code Sequence<Character>} to a {@code String}.
     *
     * @param seq
     *            the sequence to convert
     * @return the resulting string
     * @requires seq /= null
     * @ensures sequenceToString = TO_STRING(seq)
     */
    String sequenceToString(Sequence<Character> seq);

}