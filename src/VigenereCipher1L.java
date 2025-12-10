import components.sequence.Sequence;
import components.sequence.Sequence1L;

/**
 * {@code VigenereCipher} represented as a {@code Sequence<Character>} with
 * implementations of primary methods.
 *
 * @author Vikranth Vegesina
 * @convention <pre>
 * for all i: integer where (0 <= i and i < |$this.rep|)
 *   ($this.rep[i] is in ['A'..'Z'])
 * </pre>
 * @correspondence <pre>
 * this.key = $this.rep
 * </pre>
 */
public class VigenereCipher1L extends VigenereCipherSecondary {

    /*
     * Private members
     */

    /**
     * Representation of {@code this}.
     */
    private Sequence<Character> rep;

    /**
     * Alphabet used for Vigenere cipher operations.
     */
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {
        this.rep = new Sequence1L<>();
    }

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
     * Constructors
     */

    /**
     * No-argument constructor.
     */
    public VigenereCipher1L() {
        this.createNewRep();
    }

    /*
     * Standard methods
     */

    @Override
    public final void clear() {
        this.createNewRep();
    }

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
        this.rep = localSource.rep;
        localSource.createNewRep();
    }

    /*
     * Kernel methods
     */

    @Override
    public final Sequence<Character> encrypt(Sequence<Character> text) {
        assert text != null : "Violation of: text is not null";
        assert this.rep.length() > 0 : "Violation of: |this.key| > 0";

        Sequence<Character> result = text.newInstance();
        int keyLength = this.rep.length();
        int keyIndex = 0;

        for (int i = 0; i < text.length(); i++) {
            char ch = text.entry(i);
            if (isLetter(ch)) {
                char keyChar = this.rep.entry(keyIndex % keyLength);
                char encrypted = processChar(ch, keyChar, true);
                result.add(result.length(), encrypted);
                keyIndex++;
            } else {
                result.add(result.length(), ch);
            }
        }

        return result;
    }

    @Override
    public final Sequence<Character> decrypt(Sequence<Character> text) {
        assert text != null : "Violation of: text is not null";
        assert this.rep.length() > 0 : "Violation of: |this.key| > 0";

        Sequence<Character> result = text.newInstance();
        int keyLength = this.rep.length();
        int keyIndex = 0;

        for (int i = 0; i < text.length(); i++) {
            char ch = text.entry(i);
            if (isLetter(ch)) {
                char keyChar = this.rep.entry(keyIndex % keyLength);
                char decrypted = processChar(ch, keyChar, false);
                result.add(result.length(), decrypted);
                keyIndex++;
            } else {
                result.add(result.length(), ch);
            }
        }

        return result;
    }

    @Override
    public final Sequence<Character> key() {
        Sequence<Character> keyCopy = this.rep.newInstance();
        // Manually copy elements to avoid clearing this.rep
        for (int i = 0; i < this.rep.length(); i++) {
            keyCopy.add(keyCopy.length(), this.rep.entry(i));
        }
        return keyCopy;
    }

    @Override
    public final void setKey(Sequence<Character> key) {
        assert key != null : "Violation of: key is not null";
        assert key.length() > 0 : "Violation of: |key| > 0";

        this.rep.clear();
        for (int i = 0; i < key.length(); i++) {
            char c = key.entry(i);
            assert isLetter(c) : "Violation of: every character of key is a letter";
            this.rep.add(this.rep.length(), toUpperCase(c));
        }
        key.clear();
    }

}