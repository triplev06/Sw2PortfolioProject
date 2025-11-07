import javax.sound.midi.Sequence;


/**
 * Kernel interface for the {@code VigenereCipher} component.
 *
 * <p>This interface provides the core for encrypting
 * and decrypting text using a Vigenere cipher and managing a key.
 * Secondary methods are defined in {@code VigenereCipher}.</p>
 *
 * @author Vikranth Vegesina
 * @mathmodel type VigenereCipherKernel is modeled by
 *             (key: STRING of uppercase letters)
 * @initially <pre>
 *  ensures
 *    this.key = empty string
 * </pre>
 */

public interface VigenereCipherKernel
    extends Comparable<VigenereCipher>, VigenereCipherKernel {
    /**
     * Encrypts the given text using the stored key.
     *
     * @param text
     *            the text to encrypt
     * @return the resulting ciphertext
     * @requires text != null and this.key.length() > 0
     *           and every character of this.key is a letter
     * @ensures encrypt =
     *          TEXT_ENCRYPTED_WITH_KEY(text, this.key)
     */
    Sequence<Character> encrypt(Sequence<Character> text);

    /**
     * Decrypts the given text using the stored key.
     *
     * @param text
     *            the ciphertext to decrypt
     * @return the resulting text
     * @requires text != null and this.key.length() > 0
     *           and every character of this.key is a letter
     * @ensures decrypt =
     *          TEXT_DECRYPTED_WITH_KEY(text, this.key)
     */
    Sequence<Character> decrypt(Sequence<Character> text);

    /**
     * Returns the current key as a {@code Sequence<Character>}.
     *
     * @return the stored key
     * @ensures key = this.key
     */
    Sequence<Character> key();

    /**
     * Replaces the current key with the given one.
     *
     * @param key
     *            the new key to use
     * @replaces this
     * @requires key != null and key.length() > 0
     *           and every character of key is a letter
     * @ensures this.key = TO_UPPERCASE(key)
     */
    void setKey(Sequence<Character> key);
}
