package components.vigenerecipher;

// no idea why but vscode auto swaps components.sequence import
// with javax.sound.midi.Sequence import. Sorry if this causes any issues
// or messes with other methods I already made. No idea why it does that.
import javax.sound.midi.Sequence;

import components.sequence.Sequence1L;

/**
 * {@code VigenereCipher} kernel implementation represented as a
 * {@code Sequence<Character>}.
 *
 * @author Vikranth Vegesina
 *
 * @convention $this.key is a sequence of uppercase letters
 *
 * @correspondence this = $this.key
 */
public class VigenereCipher1L extends VigenereCipherSecondary {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * The encryption/decryption key stored as a sequence of characters.
     */
    private Sequence<Character> key;

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {
        this.key = new Sequence1L<>();
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public VigenereCipher1L() {
        this.createNewRep();
    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @Override
    public final VigenereCipher newInstance() {
        try {
            return this.getClass().getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(
                    "Cannot construct object of type " + this.getClass());
        }
    }

    @Override
    public final void clear() {
        this.createNewRep();
    }

    @Override
    public final void transferFrom(VigenereCipher source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof VigenereCipher1L : ""
                + "Violation of: source is of dynamic type VigenereCipher1L";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case.
         */
        VigenereCipher1L localSource = (VigenereCipher1L) source;
        this.key = localSource.key;
        localSource.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final Sequence<Character> encrypt(Sequence<Character> text) {
        assert text != null : "Violation of: text is not null";
        assert this.key.length() > 0 : "Violation of: |this.key| > 0";
        assert this.isKeyAllLetters(
                this.key) : "Violation of: every character of this.key is a letter";

        Sequence<Character> result = new Sequence1L<>();
        int keyIndex = 0;

        for (int i = 0; i < text.length(); i++) {
            char c = text.entry(i);

            if (Character.isLetter(c)) {
                boolean isUpperCase = Character.isUpperCase(c);
                char upperC = Character.toUpperCase(c);
                char keyChar = this.key.entry(keyIndex % this.key.length());

                // Calculate shift value (A=0, B=1, ..., Z=25)
                int textValue = upperC - 'A';
                int keyValue = keyChar - 'A';
                int encryptedValue = (textValue + keyValue) % 26;
                char encryptedChar = (char) ('A' + encryptedValue);

                // Preserve original case
                if (!isUpperCase) {
                    encryptedChar = Character.toLowerCase(encryptedChar);
                }

                result.add(result.length(), encryptedChar);
                keyIndex++;
            } else {
                // Non-letter characters are added unchanged
                result.add(result.length(), c);
            }
        }

        return result;
    }

    @Override
    public final Sequence<Character> decrypt(Sequence<Character> text) {
        assert text != null : "Violation of: text is not null";
        assert this.key.length() > 0 : "Violation of: |this.key| > 0";
        assert this.isKeyAllLetters(
                this.key) : "Violation of: every character of this.key is a letter";

        Sequence<Character> result = new Sequence1L<>();
        int keyIndex = 0;

        for (int i = 0; i < text.length(); i++) {
            char c = text.entry(i);

            if (Character.isLetter(c)) {
                boolean isUpperCase = Character.isUpperCase(c);
                char upperC = Character.toUpperCase(c);
                char keyChar = this.key.entry(keyIndex % this.key.length());

                // Calculate shift value (A=0, B=1, ..., Z=25)
                int textValue = upperC - 'A';
                int keyValue = keyChar - 'A';
                int decryptedValue = (textValue - keyValue + 26) % 26;
                char decryptedChar = (char) ('A' + decryptedValue);

                // Preserve original case
                if (!isUpperCase) {
                    decryptedChar = Character.toLowerCase(decryptedChar);
                }

                result.add(result.length(), decryptedChar);
                keyIndex++;
            } else {
                // Non-letter characters are added unchanged
                result.add(result.length(), c);
            }
        }

        return result;
    }

    @Override
    public final Sequence<Character> key() {
        Sequence<Character> keyCopy = new Sequence1L<>();
        for (int i = 0; i < this.key.length(); i++) {
            keyCopy.add(keyCopy.length(), this.key.entry(i));
        }
        return keyCopy;
    }

    @Override
    public final void setKey(Sequence<Character> key) {
        assert key != null : "Violation of: key is not null";
        assert key.length() > 0 : "Violation of: |key| > 0";
        assert this.isKeyAllLetters(
                key) : "Violation of: every character of key is a letter";

        this.key.clear();
        for (int i = 0; i < key.length(); i++) {
            char c = key.entry(i);
            this.key.add(this.key.length(), Character.toUpperCase(c));
        }
        key.clear();
    }

    /*
     * Private helper methods -------------------------------------------------
     */

    /**
     * Checks if all characters in the given sequence are letters.
     *
     * @param seq
     *            the sequence to check
     * @return true if all characters are letters, false otherwise
     * @requires seq is not null
     * @ensures isKeyAllLetters = (for all i: integer where 0 <= i < |seq|
     *          (seq[i] is a letter))
     */
    private boolean isKeyAllLetters(Sequence<Character> seq) {
        boolean allLetters = true;
        for (int i = 0; i < seq.length(); i++) {
            if (!Character.isLetter(seq.entry(i))) {
                allLetters = false;
                break;
            }
        }
        return allLetters;
    }

}
