package components.vigenerecipher;

import javax.sound.midi.Sequence;

import components.sequence.Sequence1L;

/**
 * Layered implementations of secondary methods for {@code VigenereCipher}.
 *
 * @author Vikranth Vegesina
 */
public abstract class VigenereCipherSecondary implements VigenereCipher {

    /*
     * Common methods (from Object) -------------------------------------------
     */

    @Override
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof VigenereCipher)) {
            return false;
        }
        VigenereCipher cipher = (VigenereCipher) obj;
        Sequence<Character> thisKey = this.key();
        Sequence<Character> cipherKey = cipher.key();
        return thisKey.equals(cipherKey);
    }

    @Override
    public int hashCode() {
        return this.key().hashCode();
    }

    @Override
    public String toString() {
        return "VigenereCipher[key=" + this.keyToString() + "]";
    }

    /*
     * Secondary methods ------------------------------------------------------
     */

    @Override
    public final Sequence<Character> encryptWithKey(Sequence<Character> text,
            Sequence<Character> key) {
        assert text != null : "Violation of: text is not null";
        assert key != null : "Violation of: key is not null";
        assert key.length() > 0 : "Violation of: |key| > 0";

        // Create temporary copies to avoid modifying parameters
        Sequence<Character> textCopy = text.newInstance();
        textCopy.transferFrom(text);

        Sequence<Character> keyCopy = key.newInstance();
        for (int i = 0; i < key.length(); i++) {
            keyCopy.add(keyCopy.length(), key.entry(i));
        }

        // Make key uppercase
        this.makeUpperCase(keyCopy);

        // Store current key
        Sequence<Character> savedKey = this.key();

        // Set temporary key
        this.setKey(keyCopy);

        // Encrypt
        Sequence<Character> result = this.encrypt(textCopy);

        // Restore original key
        this.setKey(savedKey);

        // Restore text
        text.transferFrom(textCopy);

        return result;
    }

    @Override
    public final Sequence<Character> decryptWithKey(Sequence<Character> text,
            Sequence<Character> key) {
        assert text != null : "Violation of: text is not null";
        assert key != null : "Violation of: key is not null";
        assert key.length() > 0 : "Violation of: |key| > 0";

        // Create temporary copies to avoid modifying parameters
        Sequence<Character> textCopy = text.newInstance();
        textCopy.transferFrom(text);

        Sequence<Character> keyCopy = key.newInstance();
        for (int i = 0; i < key.length(); i++) {
            keyCopy.add(keyCopy.length(), key.entry(i));
        }

        // Make key uppercase
        this.makeUpperCase(keyCopy);

        // Store current key
        Sequence<Character> savedKey = this.key();

        // Set temporary key
        this.setKey(keyCopy);

        // Decrypt
        Sequence<Character> result = this.decrypt(textCopy);

        // Restore original key
        this.setKey(savedKey);

        // Restore text
        text.transferFrom(textCopy);

        return result;
    }

    @Override
    public final boolean isValidKey() {
        Sequence<Character> currentKey = this.key();
        boolean valid = currentKey.length() > 0;

        if (valid) {
            for (int i = 0; i < currentKey.length(); i++) {
                char c = currentKey.entry(i);
                if (!Character.isLetter(c)) {
                    valid = false;
                    break;
                }
            }
        }

        return valid;
    }

    @Override
    public final void setKeyFromString(String keyStr) {
        assert keyStr != null : "Violation of: keyStr is not null";
        assert keyStr.length() > 0 : "Violation of: |keyStr| > 0";

        Sequence<Character> keySeq = this.stringToSequence(keyStr);
        this.setKey(keySeq);
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

    /*
     * Helper methods ---------------------------------------------------------
     */

    /**
     * Converts all characters in the sequence to uppercase.
     *
     * @param seq
     *            the sequence to convert
     * @updates seq
     * @ensures seq contains the uppercase version of #seq
     */
    private void makeUpperCase(Sequence<Character> seq) {
        for (int i = 0; i < seq.length(); i++) {
            char c = seq.entry(i);
            if (Character.isLetter(c)) {
                seq.replaceEntry(i, Character.toUpperCase(c));
            }
        }
    }

}