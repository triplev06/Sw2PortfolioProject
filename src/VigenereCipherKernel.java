import components.sequence.Sequence;
import components.standard.Standard;

/**
 * Kernel interface for the {@code VigenereCipher} component.
 *
 * <p>
 * This interface provides the core operations for encrypting and decrypting
 * text using a Vigenere cipher and managing a key.
 * </p>
 *
 * @author Vikranth Vegesina
 * @mathmodel type VigenereCipherKernel is modeled by (key: SEQUENCE of
 *            uppercase letters)
 * @initially <pre>
 * ensures
 *   this.key = empty_sequence
 * </pre>
 */
public interface VigenereCipherKernel extends Standard<VigenereCipher> {

    /**
     * Encrypts the given text using the stored key.
     *
     * @param text
     *            the text to encrypt
     * @return the resulting ciphertext
     * @requires text /= null and |this.key| > 0 and every character of this.key
     *           is a letter
     * @ensures encrypt = TEXT_ENCRYPTED_WITH_KEY(text, this.key)
     */
    Sequence<Character> encrypt(Sequence<Character> text);

    /**
     * Decrypts the given text using the stored key.
     *
     * @param text
     *            the ciphertext to decrypt
     * @return the resulting plaintext
     * @requires text /= null and |this.key| > 0 and every character of this.key
     *           is a letter
     * @ensures decrypt = TEXT_DECRYPTED_WITH_KEY(text, this.key)
     */
    Sequence<Character> decrypt(Sequence<Character> text);

    /**
     * Returns a copy of the current key.
     *
     * @return a copy of the stored key
     * @ensures key = this.key
     */
    Sequence<Character> key();

    /**
     * Replaces the current key with the given one.
     *
     * @param key
     *            the new key to use
     * @replaces this.key
     * @clears key
     * @requires key /= null and |key| > 0 and every character of key is a
     *           letter
     * @ensures this.key = TO_UPPERCASE(#key)
     */
    void setKey(Sequence<Character> key);

}
