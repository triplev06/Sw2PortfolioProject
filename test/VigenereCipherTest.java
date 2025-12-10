import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.sequence.Sequence;
import components.sequence.Sequence1L;

/**
 * JUnit test fixture for {@code VigenereCipher}'s secondary methods.
 *
 * @author Vikranth Vegesina
 */
public class VigenereCipherTest {

    /**
     * Helper method to create a Sequence from a String.
     *
     * @param s
     *            the string to convert
     * @return sequence of characters
     */
    private static Sequence<Character> createSequence(String s) {
        Sequence<Character> seq = new Sequence1L<>();
        for (int i = 0; i < s.length(); i++) {
            seq.add(seq.length(), s.charAt(i));
        }
        return seq;
    }

    /**
     * Helper method to convert a Sequence to a String.
     *
     * @param seq
     *            the sequence to convert
     * @return string representation
     */
    private static String sequenceToString(Sequence<Character> seq) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < seq.length(); i++) {
            sb.append(seq.entry(i));
        }
        return sb.toString();
    }

    /*
     * Test cases for encryptWithKey
     */

    @Test
    public void testEncryptWithKeySimple() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> storedKey = createSequence("ORIGINAL");
        cipher.setKey(storedKey);

        Sequence<Character> text = createSequence("HELLO");
        Sequence<Character> textCopy = createSequence("HELLO");
        Sequence<Character> tempKey = createSequence("TEMP");
        Sequence<Character> tempKeyCopy = createSequence("TEMP");

        Sequence<Character> encrypted = cipher.encryptWithKey(text, tempKey);

        assertEquals(textCopy, text); // text unchanged
        assertEquals(tempKeyCopy, tempKey); // key unchanged
        assertEquals("ORIGINAL", sequenceToString(cipher.key())); // stored key unchanged
        assertEquals("AYHSQ", sequenceToString(encrypted));
    }

    @Test
    public void testEncryptWithKeyDifferentFromStored() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> storedKey = createSequence("KEY");
        cipher.setKey(storedKey);

        Sequence<Character> text = createSequence("HELLO");
        Sequence<Character> tempKey = createSequence("ABC");

        Sequence<Character> encrypted = cipher.encryptWithKey(text, tempKey);

        assertEquals("KEY", sequenceToString(cipher.key())); // stored key unchanged
        assertEquals("HFNLP", sequenceToString(encrypted));
    }

    @Test
    public void testEncryptWithKeyWithSpaces() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> storedKey = createSequence("KEY");
        cipher.setKey(storedKey);

        Sequence<Character> text = createSequence("HELLO WORLD");
        Sequence<Character> tempKey = createSequence("SECRET");

        Sequence<Character> encrypted = cipher.encryptWithKey(text, tempKey);

        assertEquals("KEY", sequenceToString(cipher.key()));
        assertEquals("ZINZC HDVZR", sequenceToString(encrypted));
    }

    /*
     * Test cases for decryptWithKey
     */

    @Test
    public void testDecryptWithKeySimple() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> storedKey = createSequence("ORIGINAL");
        cipher.setKey(storedKey);

        Sequence<Character> encrypted = createSequence("AYHSQ");
        Sequence<Character> encryptedCopy = createSequence("AYHSQ");
        Sequence<Character> tempKey = createSequence("TEMP");
        Sequence<Character> tempKeyCopy = createSequence("TEMP");

        Sequence<Character> decrypted = cipher.decryptWithKey(encrypted, tempKey);

        assertEquals(encryptedCopy, encrypted); // encrypted unchanged
        assertEquals(tempKeyCopy, tempKey); // key unchanged
        assertEquals("ORIGINAL", sequenceToString(cipher.key())); // stored key unchanged
        assertEquals("HELLO", sequenceToString(decrypted));
    }

    @Test
    public void testDecryptWithKeyDifferentFromStored() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> storedKey = createSequence("KEY");
        cipher.setKey(storedKey);

        Sequence<Character> encrypted = createSequence("HFNLP");
        Sequence<Character> tempKey = createSequence("ABC");

        Sequence<Character> decrypted = cipher.decryptWithKey(encrypted, tempKey);

        assertEquals("KEY", sequenceToString(cipher.key())); // stored key unchanged
        assertEquals("HELLO", sequenceToString(decrypted));
    }

    @Test
    public void testDecryptWithKeyWithSpaces() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> storedKey = createSequence("KEY");
        cipher.setKey(storedKey);

        Sequence<Character> encrypted = createSequence("ZINZC HDVZR");
        Sequence<Character> tempKey = createSequence("SECRET");

        Sequence<Character> decrypted = cipher.decryptWithKey(encrypted, tempKey);

        assertEquals("KEY", sequenceToString(cipher.key()));
        assertEquals("HELLO WORLD", sequenceToString(decrypted));
    }

    /*
     * Test cases for encryptWithKey/decryptWithKey round trip
     */

    @Test
    public void testEncryptDecryptWithKeyRoundTrip() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> storedKey = createSequence("STORED");
        cipher.setKey(storedKey);

        Sequence<Character> original = createSequence("SECRET MESSAGE");
        Sequence<Character> originalCopy = createSequence("SECRET MESSAGE");
        Sequence<Character> tempKey = createSequence("TEMPORARY");

        Sequence<Character> encrypted = cipher.encryptWithKey(original, tempKey);
        Sequence<Character> decrypted = cipher.decryptWithKey(encrypted, tempKey);

        assertEquals(originalCopy, original);
        assertEquals("SECRET MESSAGE", sequenceToString(decrypted));
        assertEquals("STORED", sequenceToString(cipher.key())); // stored key unchanged
    }

    /*
     * Test cases for isValidKey
     */

    @Test
    public void testIsValidKeyEmpty() {
        VigenereCipher cipher = new VigenereCipher1L();
        assertFalse(cipher.isValidKey());
    }

    @Test
    public void testIsValidKeyLettersOnly() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> key = createSequence("KEY");
        cipher.setKey(key);

        assertTrue(cipher.isValidKey());
    }

    @Test
    public void testIsValidKeyUppercase() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> key = createSequence("ABCDEF");
        cipher.setKey(key);

        assertTrue(cipher.isValidKey());
    }

    @Test
    public void testIsValidKeyLowercaseConverted() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> key = createSequence("abc");
        cipher.setKey(key);

        assertTrue(cipher.isValidKey());
    }

    @Test
    public void testIsValidKeyAfterClear() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> key = createSequence("KEY");
        cipher.setKey(key);

        cipher.clear();

        assertFalse(cipher.isValidKey());
    }

    /*
     * Test cases for setKeyFromString
     */

    @Test
    public void testSetKeyFromStringSimple() {
        VigenereCipher cipher = new VigenereCipher1L();

        cipher.setKeyFromString("KEY");

        assertEquals("KEY", sequenceToString(cipher.key()));
    }

    @Test
    public void testSetKeyFromStringLowercase() {
        VigenereCipher cipher = new VigenereCipher1L();

        cipher.setKeyFromString("abc");

        assertEquals("ABC", sequenceToString(cipher.key()));
    }

    @Test
    public void testSetKeyFromStringMixedCase() {
        VigenereCipher cipher = new VigenereCipher1L();

        cipher.setKeyFromString("AbCdEf");

        assertEquals("ABCDEF", sequenceToString(cipher.key()));
    }

    @Test
    public void testSetKeyFromStringReplace() {
        VigenereCipher cipher = new VigenereCipher1L();
        cipher.setKeyFromString("FIRST");

        cipher.setKeyFromString("SECOND");

        assertEquals("SECOND", sequenceToString(cipher.key()));
    }

    /*
     * Test cases for keyToString
     */

    @Test
    public void testKeyToStringSimple() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> key = createSequence("KEY");
        cipher.setKey(key);

        String keyString = cipher.keyToString();

        assertEquals("KEY", keyString);
        assertEquals("KEY", sequenceToString(cipher.key())); // cipher unchanged
    }

    @Test
    public void testKeyToStringLong() {
        VigenereCipher cipher = new VigenereCipher1L();
        cipher.setKeyFromString("THISISALONGKEY");

        String keyString = cipher.keyToString();

        assertEquals("THISISALONGKEY", keyString);
    }

    @Test
    public void testKeyToStringAfterEncrypt() {
        VigenereCipher cipher = new VigenereCipher1L();
        cipher.setKeyFromString("KEY");

        Sequence<Character> text = createSequence("HELLO");
        cipher.encrypt(text);

        String keyString = cipher.keyToString();

        assertEquals("KEY", keyString);
    }

    /*
     * Test cases for stringToSequence
     */

    @Test
    public void testStringToSequenceSimple() {
        VigenereCipher cipher = new VigenereCipher1L();

        Sequence<Character> seq = cipher.stringToSequence("HELLO");

        assertEquals("HELLO", sequenceToString(seq));
    }

    @Test
    public void testStringToSequenceEmpty() {
        VigenereCipher cipher = new VigenereCipher1L();

        Sequence<Character> seq = cipher.stringToSequence("");

        assertEquals("", sequenceToString(seq));
        assertEquals(0, seq.length());
    }

    @Test
    public void testStringToSequenceWithSpaces() {
        VigenereCipher cipher = new VigenereCipher1L();

        Sequence<Character> seq = cipher.stringToSequence("HELLO WORLD");

        assertEquals("HELLO WORLD", sequenceToString(seq));
    }

    @Test
    public void testStringToSequenceWithNumbers() {
        VigenereCipher cipher = new VigenereCipher1L();

        Sequence<Character> seq = cipher.stringToSequence("ABC123");

        assertEquals("ABC123", sequenceToString(seq));
    }

    @Test
    public void testStringToSequenceWithPunctuation() {
        VigenereCipher cipher = new VigenereCipher1L();

        Sequence<Character> seq = cipher.stringToSequence("HELLO, WORLD!");

        assertEquals("HELLO, WORLD!", sequenceToString(seq));
    }

    /*
     * Test cases for sequenceToString
     */

    @Test
    public void testSequenceToStringSimple() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> seq = createSequence("HELLO");
        Sequence<Character> seqCopy = createSequence("HELLO");

        String result = cipher.sequenceToString(seq);

        assertEquals("HELLO", result);
        assertEquals(seqCopy, seq); // sequence unchanged
    }

    @Test
    public void testSequenceToStringEmpty() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> seq = createSequence("");
        Sequence<Character> seqCopy = createSequence("");

        String result = cipher.sequenceToString(seq);

        assertEquals("", result);
        assertEquals(seqCopy, seq);
    }

    @Test
    public void testSequenceToStringWithSpaces() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> seq = createSequence("HELLO WORLD");

        String result = cipher.sequenceToString(seq);

        assertEquals("HELLO WORLD", result);
    }

    @Test
    public void testSequenceToStringWithNumbers() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> seq = createSequence("ABC123");

        String result = cipher.sequenceToString(seq);

        assertEquals("ABC123", result);
    }

    /*
     * Test cases for stringToSequence/sequenceToString round trip
     */

    @Test
    public void testStringSequenceRoundTrip() {
        VigenereCipher cipher = new VigenereCipher1L();
        String original = "HELLO WORLD 123!";

        Sequence<Character> seq = cipher.stringToSequence(original);
        String result = cipher.sequenceToString(seq);

        assertEquals(original, result);
    }

    @Test
    public void testSequenceStringRoundTrip() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> original = createSequence("HELLO WORLD 123!");
        Sequence<Character> originalCopy = createSequence("HELLO WORLD 123!");

        String str = cipher.sequenceToString(original);
        Sequence<Character> result = cipher.stringToSequence(str);

        assertEquals(originalCopy, original);
        assertEquals(sequenceToString(originalCopy), sequenceToString(result));
    }

    /*
     * Integration tests combining multiple secondary methods
     */

    @Test
    public void testIntegrationSetKeyFromStringAndEncrypt() {
        VigenereCipher cipher = new VigenereCipher1L();
        cipher.setKeyFromString("SECRET");

        Sequence<Character> text = cipher.stringToSequence("ATTACK AT DAWN");
        Sequence<Character> encrypted = cipher.encrypt(text);
        String encryptedString = cipher.sequenceToString(encrypted);

        assertEquals("SXVRGD SX QANO", encryptedString);
    }

    @Test
    public void testIntegrationFullWorkflow() {
        VigenereCipher cipher = new VigenereCipher1L();

        // Set key from string
        cipher.setKeyFromString("CIPHER");

        // Verify key
        assertTrue(cipher.isValidKey());
        assertEquals("CIPHER", cipher.keyToString());

        // Encrypt with stored key
        Sequence<Character> plain = cipher.stringToSequence("HELLO WORLD");
        Sequence<Character> encrypted = cipher.encrypt(plain);
        assertEquals("JTFPQ YQEUO", cipher.sequenceToString(encrypted));

        // Decrypt with stored key
        Sequence<Character> decrypted = cipher.decrypt(encrypted);
        assertEquals("HELLO WORLD", cipher.sequenceToString(decrypted));

        // Encrypt with temporary key
        Sequence<Character> tempKey = cipher.stringToSequence("TEMP");
        Sequence<Character> encrypted2 = cipher.encryptWithKey(plain, tempKey);
        assertEquals("AYHSQ KQFUP", cipher.sequenceToString(encrypted2));

        // Verify stored key unchanged
        assertEquals("CIPHER", cipher.keyToString());
    }

}