package components.vigenerecipher;

import javax.sound.midi.Sequence;

/**
 * Enhanced {@code VigenereCipherKernel} with secondary methods.
 *
 * <p>
 * This interface adds convenience operations such as encrypting/decrypting with
 * a different key, converting between {@code Sequence<Character>} and
 * {@code String}, and checking if the stored key is valid.
 * </p>
 *
 * @author Vikranth Vegesina
 *
 * @mathdefinitions
 *
 *                  <pre>
 * VIGENERE_SHIFT(c: character, k: character): character is
 *   if c is a letter then
 *     shifted character using Vigenere cipher with key character k
 *   else
 *     c
 *
 * TEXT_ENCRYPTED_WITH_KEY(text: string of character, key: string of character): string of character satisfies
 *   |TEXT_ENCRYPTED_WITH_KEY(text, key)| = |text| and
 *   for all i: integer where 0 <= i < |text| and
 *     letter_count is the count of letters in text[0, i)
 *     (TEXT_ENCRYPTED_WITH_KEY(text, key)[i] =
 *       VIGENERE_SHIFT(text[i], key[letter_count mod |key|]))
 *
 * TEXT_DECRYPTED_WITH_KEY(text: string of character, key: string of character): string of character is
 *   the inverse operation of TEXT_ENCRYPTED_WITH_KEY
 *                  </pre>
 */
public interface VigenereCipher extends VigenereCipherKernel {

    /**
     * Encrypts the given text using the provided key. This method does not
     * change the stored key.
     *
     * @param text
     *            the text to encrypt
     * @param key
     *            the key to use for encryption
     * @return the ciphertext produced
     * @requires text is not null and key is not null and |key| > 0 and every
     *           character of key is a letter
     * @ensures encryptWithKey = TEXT_ENCRYPTED_WITH_KEY(text,
     *          TO_UPPERCASE(key))
     */
    Sequence<Character> encryptWithKey(Sequence<Character> text,
            Sequence<Character> key);

    /**
     * Decrypts the given ciphertext using the provided key. This method does
     * not change the stored key.
     *
     * @param text
     *            the ciphertext to decrypt
     * @param key
     *            the key to use for decryption
     * @return the plaintext produced
     * @requires text is not null and key is not null and |key| > 0 and every
     *           character of key is a letter
     * @ensures decryptWithKey = TEXT_DECRYPTED_WITH_KEY(text,
     *          TO_UPPERCASE(key))
     */
    Sequence<Character> decryptWithKey(Sequence<Character> text,
            Sequence<Character> key);

    /**
     * Checks whether the stored key is valid. A valid key is non-empty and made
     * up only of letters.
     *
     * @return true if the key is valid, false otherwise
     * @ensures isValidKey = (|this.key| > 0 and every element of this.key is a
     *          letter)
     */
    boolean isValidKey();

    /**
     * Sets the key from the given {@code String}. Only letters are allowed.
     *
     * @param keyStr
     *            the new key as a string
     * @replaces this.key
     * @requires keyStr is not null and |keyStr| > 0 and every character of
     *           keyStr is a letter
     * @ensures this.key = TO_UPPERCASE(CHAR_SEQUENCE_OF(keyStr))
     */
    void setKeyFromString(String keyStr);

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
     * @requires s is not null
     * @ensures stringToSequence = CHAR_SEQUENCE_OF(s)
     */
    Sequence<Character> stringToSequence(String s);

    /**
     * Converts the given {@code Sequence<Character>} to a {@code String}.
     *
     * @param seq
     *            the sequence to convert
     * @return the resulting string
     * @requires seq is not null
     * @ensures sequenceToString = TO_STRING(seq)
     */
    String sequenceToString(Sequence<Character> seq);

}