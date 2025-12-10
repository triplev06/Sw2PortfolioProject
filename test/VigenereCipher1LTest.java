import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.sequence.Sequence;
import components.sequence.Sequence1L;

/**
 * JUnit test fixture for {@code VigenereCipher1L}.
 *
 * @author Vikranth Vegesina
 */
public class VigenereCipher1LTest {

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
     * Test cases for constructor
     */

    @Test
    public void testConstructor() {
        VigenereCipher cipher = new VigenereCipher1L();
        assertEquals(0, cipher.key().length());
    }

    /*
     * Test cases for setKey (kernel method)
     */

    @Test
    public void testSetKeySimple() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> key = createSequence("KEY");

        cipher.setKey(key);

        assertEquals("KEY", sequenceToString(cipher.key()));
        assertEquals(0, key.length()); // key should be cleared
    }

    @Test
    public void testSetKeyLowercase() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> key = createSequence("abc");

        cipher.setKey(key);

        assertEquals("ABC", sequenceToString(cipher.key()));
        assertEquals(0, key.length());
    }

    @Test
    public void testSetKeyMixedCase() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> key = createSequence("AbCdEf");

        cipher.setKey(key);

        assertEquals("ABCDEF", sequenceToString(cipher.key()));
        assertEquals(0, key.length());
    }

    @Test
    public void testSetKeyReplace() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> key1 = createSequence("FIRST");
        cipher.setKey(key1);

        Sequence<Character> key2 = createSequence("SECOND");
        cipher.setKey(key2);

        assertEquals("SECOND", sequenceToString(cipher.key()));
        assertEquals(0, key2.length());
    }

    /*
     * Test cases for key (kernel method)
     */

    @Test
    public void testKeyAfterSet() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> key = createSequence("TEST");
        cipher.setKey(key);

        Sequence<Character> retrieved = cipher.key();

        assertEquals("TEST", sequenceToString(retrieved));
        assertEquals("TEST", sequenceToString(cipher.key())); // verify unchanged
    }

    @Test
    public void testKeyReturnsIndependentCopy() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> key = createSequence("KEY");
        cipher.setKey(key);

        Sequence<Character> retrieved = cipher.key();
        retrieved.add(0, 'X'); // modify copy

        assertEquals("KEY", sequenceToString(cipher.key())); // original unchanged
    }

    /*
     * Test cases for encrypt (kernel method)
     */

    @Test
    public void testEncryptSimple() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> key = createSequence("KEY");
        cipher.setKey(key);

        Sequence<Character> plain = createSequence("HELLO");
        Sequence<Character> encrypted = cipher.encrypt(plain);

        assertEquals("RIJVS", sequenceToString(encrypted));
        assertEquals("HELLO", sequenceToString(plain)); // unchanged
        assertEquals("KEY", sequenceToString(cipher.key())); // unchanged
    }

    @Test
    public void testEncryptWithSpaces() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> key = createSequence("KEY");
        cipher.setKey(key);

        Sequence<Character> plain = createSequence("HELLO WORLD");
        Sequence<Character> encrypted = cipher.encrypt(plain);

        assertEquals("RIJVS UYVJN", sequenceToString(encrypted));
        assertEquals("HELLO WORLD", sequenceToString(plain));
    }

    @Test
    public void testEncryptLowercase() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> key = createSequence("KEY");
        cipher.setKey(key);

        Sequence<Character> plain = createSequence("hello");
        Sequence<Character> encrypted = cipher.encrypt(plain);

        assertEquals("RIJVS", sequenceToString(encrypted));
    }

    @Test
    public void testEncryptMixedCase() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> key = createSequence("KEY");
        cipher.setKey(key);

        Sequence<Character> plain = createSequence("HeLLo WoRLd");
        Sequence<Character> encrypted = cipher.encrypt(plain);

        assertEquals("RIJVS UYVJN", sequenceToString(encrypted));
    }

    @Test
    public void testEncryptWithPunctuation() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> key = createSequence("KEY");
        cipher.setKey(key);

        Sequence<Character> plain = createSequence("HELLO, WORLD!");
        Sequence<Character> encrypted = cipher.encrypt(plain);

        assertEquals("RIJVS, UYVJN!", sequenceToString(encrypted));
    }

    @Test
    public void testEncryptKeyWraparound() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> key = createSequence("AB");
        cipher.setKey(key);

        Sequence<Character> plain = createSequence("HELLO");
        Sequence<Character> encrypted = cipher.encrypt(plain);

        assertEquals("HFNLP", sequenceToString(encrypted));
    }

    /*
     * Test cases for decrypt (kernel method)
     */

    @Test
    public void testDecryptSimple() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> key = createSequence("KEY");
        cipher.setKey(key);

        Sequence<Character> encrypted = createSequence("RIJVS");
        Sequence<Character> decrypted = cipher.decrypt(encrypted);

        assertEquals("HELLO", sequenceToString(decrypted));
        assertEquals("RIJVS", sequenceToString(encrypted)); // unchanged
        assertEquals("KEY", sequenceToString(cipher.key())); // unchanged
    }

    @Test
    public void testDecryptWithSpaces() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> key = createSequence("KEY");
        cipher.setKey(key);

        Sequence<Character> encrypted = createSequence("RIJVS UYVJN");
        Sequence<Character> decrypted = cipher.decrypt(encrypted);

        assertEquals("HELLO WORLD", sequenceToString(decrypted));
    }

    @Test
    public void testDecryptWithPunctuation() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> key = createSequence("KEY");
        cipher.setKey(key);

        Sequence<Character> encrypted = createSequence("RIJVS, UYVJN!");
        Sequence<Character> decrypted = cipher.decrypt(encrypted);

        assertEquals("HELLO, WORLD!", sequenceToString(decrypted));
    }

    /*
     * Test cases for encrypt/decrypt round trip
     */

    @Test
    public void testEncryptDecryptRoundTrip() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> key = createSequence("SECRET");
        cipher.setKey(key);

        Sequence<Character> original = createSequence("ATTACK AT DAWN");
        Sequence<Character> encrypted = cipher.encrypt(original);
        Sequence<Character> decrypted = cipher.decrypt(encrypted);

        assertEquals("ATTACK AT DAWN", sequenceToString(decrypted));
        assertEquals("ATTACK AT DAWN", sequenceToString(original)); // unchanged
    }

    @Test
    public void testEncryptDecryptEmptySpaces() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> key = createSequence("KEY");
        cipher.setKey(key);

        Sequence<Character> original = createSequence("   ");
        Sequence<Character> encrypted = cipher.encrypt(original);
        Sequence<Character> decrypted = cipher.decrypt(encrypted);

        assertEquals("   ", sequenceToString(encrypted));
        assertEquals("   ", sequenceToString(decrypted));
    }

    /*
     * Test cases for clear (Standard method)
     */

    @Test
    public void testClearFromEmpty() {
        VigenereCipher cipher = new VigenereCipher1L();

        cipher.clear();

        assertEquals(0, cipher.key().length());
    }

    @Test
    public void testClearFromNonEmpty() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> key = createSequence("KEY");
        cipher.setKey(key);

        cipher.clear();

        assertEquals(0, cipher.key().length());
    }

    /*
     * Test cases for newInstance (Standard method)
     */

    @Test
    public void testNewInstanceEmpty() {
        VigenereCipher cipher = new VigenereCipher1L();

        VigenereCipher cipherNew = cipher.newInstance();

        assertEquals(0, cipherNew.key().length());
        assertTrue(cipherNew instanceof VigenereCipher1L);
    }

    @Test
    public void testNewInstanceNonEmpty() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> key = createSequence("KEY");
        cipher.setKey(key);

        VigenereCipher cipherNew = cipher.newInstance();

        assertEquals(0, cipherNew.key().length());
        assertEquals("KEY", sequenceToString(cipher.key())); // original unchanged
    }

    /*
     * Test cases for transferFrom (Standard method)
     */

    @Test
    public void testTransferFromEmpty() {
        VigenereCipher cipher1 = new VigenereCipher1L();
        VigenereCipher cipher2 = new VigenereCipher1L();

        cipher1.transferFrom(cipher2);

        assertEquals(0, cipher1.key().length());
        assertEquals(0, cipher2.key().length());
    }

    @Test
    public void testTransferFromNonEmpty() {
        VigenereCipher cipher1 = new VigenereCipher1L();
        VigenereCipher cipher2 = new VigenereCipher1L();
        Sequence<Character> key = createSequence("SOURCE");
        cipher2.setKey(key);

        cipher1.transferFrom(cipher2);

        assertEquals("SOURCE", sequenceToString(cipher1.key()));
        assertEquals(0, cipher2.key().length()); // source should be empty
    }

    @Test
    public void testTransferFromReplace() {
        VigenereCipher cipher1 = new VigenereCipher1L();
        Sequence<Character> key1 = createSequence("FIRST");
        cipher1.setKey(key1);

        VigenereCipher cipher2 = new VigenereCipher1L();
        Sequence<Character> key2 = createSequence("SECOND");
        cipher2.setKey(key2);

        cipher1.transferFrom(cipher2);

        assertEquals("SECOND", sequenceToString(cipher1.key()));
        assertEquals(0, cipher2.key().length());
    }

    /*
     * Test cases for encryptWithKey (secondary method)
     */

    @Test
    public void testEncryptWithKeySimple() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> storedKey = createSequence("ORIGINAL");
        cipher.setKey(storedKey);

        Sequence<Character> text = createSequence("HELLO");
        Sequence<Character> tempKey = createSequence("TEMP");

        Sequence<Character> encrypted = cipher.encryptWithKey(text, tempKey);

        assertEquals("AYHSQ", sequenceToString(encrypted));
        assertEquals("HELLO", sequenceToString(text)); // text unchanged
        assertEquals("TEMP", sequenceToString(tempKey)); // temp key unchanged
        assertEquals("ORIGINAL", sequenceToString(cipher.key())); // stored key unchanged
    }

    @Test
    public void testEncryptWithKeyDifferent() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> storedKey = createSequence("KEY");
        cipher.setKey(storedKey);

        Sequence<Character> text = createSequence("HELLO");
        Sequence<Character> tempKey = createSequence("ABC");

        Sequence<Character> encrypted = cipher.encryptWithKey(text, tempKey);

        assertEquals("HFNLP", sequenceToString(encrypted));
        assertEquals("KEY", sequenceToString(cipher.key())); // stored key unchanged
    }

    /*
     * Test cases for decryptWithKey (secondary method)
     */

    @Test
    public void testDecryptWithKeySimple() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> storedKey = createSequence("ORIGINAL");
        cipher.setKey(storedKey);

        Sequence<Character> encrypted = createSequence("AYHSQ");
        Sequence<Character> tempKey = createSequence("TEMP");

        Sequence<Character> decrypted = cipher.decryptWithKey(encrypted, tempKey);

        assertEquals("HELLO", sequenceToString(decrypted));
        assertEquals("AYHSQ", sequenceToString(encrypted)); // encrypted unchanged
        assertEquals("TEMP", sequenceToString(tempKey)); // temp key unchanged
        assertEquals("ORIGINAL", sequenceToString(cipher.key())); // stored key unchanged
    }

    @Test
    public void testEncryptDecryptWithKeyRoundTrip() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> storedKey = createSequence("STORED");
        cipher.setKey(storedKey);

        Sequence<Character> original = createSequence("SECRET MESSAGE");
        Sequence<Character> tempKey = createSequence("TEMPORARY");

        Sequence<Character> encrypted = cipher.encryptWithKey(original, tempKey);
        Sequence<Character> decrypted = cipher.decryptWithKey(encrypted, tempKey);

        assertEquals("SECRET MESSAGE", sequenceToString(decrypted));
        assertEquals("STORED", sequenceToString(cipher.key())); // stored key unchanged
    }

    /*
     * Test cases for isValidKey (secondary method)
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
    public void testIsValidKeyAfterClear() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> key = createSequence("KEY");
        cipher.setKey(key);

        cipher.clear();

        assertFalse(cipher.isValidKey());
    }

    /*
     * Test cases for setKeyFromString (secondary method)
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
    public void testSetKeyFromStringReplace() {
        VigenereCipher cipher = new VigenereCipher1L();
        cipher.setKeyFromString("FIRST");

        cipher.setKeyFromString("SECOND");

        assertEquals("SECOND", sequenceToString(cipher.key()));
    }

    /*
     * Test cases for keyToString (secondary method)
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
    public void testKeyToStringAfterEncrypt() {
        VigenereCipher cipher = new VigenereCipher1L();
        cipher.setKeyFromString("KEY");
        Sequence<Character> text = createSequence("HELLO");
        cipher.encrypt(text);

        String keyString = cipher.keyToString();

        assertEquals("KEY", keyString);
    }

    /*
     * Test cases for stringToSequence (secondary method)
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

    /*
     * Test cases for sequenceToString (secondary method)
     */

    @Test
    public void testSequenceToStringSimple() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> seq = createSequence("HELLO");

        String result = cipher.sequenceToString(seq);

        assertEquals("HELLO", result);
        assertEquals("HELLO", sequenceToString(seq)); // sequence unchanged
    }

    @Test
    public void testSequenceToStringEmpty() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> seq = createSequence("");

        String result = cipher.sequenceToString(seq);

        assertEquals("", result);
    }

    /*
     * Round trip tests
     */

    @Test
    public void testStringSequenceRoundTrip() {
        VigenereCipher cipher = new VigenereCipher1L();
        String original = "HELLO WORLD 123!";

        Sequence<Character> seq = cipher.stringToSequence(original);
        String result = cipher.sequenceToString(seq);

        assertEquals(original, result);
    }

}