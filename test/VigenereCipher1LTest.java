import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.sequence.Sequence;
import components.sequence.Sequence1L;

/**
 * JUnit test fixture for {@code VigenereCipher1L}'s kernel methods.
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
        VigenereCipher cipherExpected = new VigenereCipher1L();
        assertEquals(cipherExpected, cipher);
    }

    /*
     * Test cases for setKey
     */

    @Test
    public void testSetKeySimple() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> key = createSequence("KEY");
        Sequence<Character> keyExpected = createSequence("");

        cipher.setKey(key);

        assertEquals(keyExpected, key); // key should be cleared
        assertEquals("KEY", sequenceToString(cipher.key()));
    }

    @Test
    public void testSetKeyLowercase() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> key = createSequence("abc");
        Sequence<Character> keyExpected = createSequence("");

        cipher.setKey(key);

        assertEquals(keyExpected, key); // key should be cleared
        assertEquals("ABC", sequenceToString(cipher.key()));
    }

    @Test
    public void testSetKeyMixedCase() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> key = createSequence("AbCdEf");
        Sequence<Character> keyExpected = createSequence("");

        cipher.setKey(key);

        assertEquals(keyExpected, key); // key should be cleared
        assertEquals("ABCDEF", sequenceToString(cipher.key()));
    }

    @Test
    public void testSetKeyReplace() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> key1 = createSequence("FIRST");
        cipher.setKey(key1);

        Sequence<Character> key2 = createSequence("SECOND");
        Sequence<Character> key2Expected = createSequence("");

        cipher.setKey(key2);

        assertEquals(key2Expected, key2);
        assertEquals("SECOND", sequenceToString(cipher.key()));
    }

    /*
     * Test cases for key
     */

    @Test
    public void testKeyAfterSet() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> key = createSequence("TEST");
        cipher.setKey(key);

        Sequence<Character> retrieved = cipher.key();
        assertEquals("TEST", sequenceToString(retrieved));
        assertEquals("TEST", sequenceToString(cipher.key())); // verify cipher unchanged
    }

    @Test
    public void testKeyReturnsIndependentCopy() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> key = createSequence("KEY");
        cipher.setKey(key);

        Sequence<Character> retrieved = cipher.key();
        retrieved.add(0, 'X'); // modify the copy

        assertEquals("KEY", sequenceToString(cipher.key())); // original unchanged
    }

    /*
     * Test cases for encrypt
     */

    @Test
    public void testEncryptSimple() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> key = createSequence("KEY");
        cipher.setKey(key);

        Sequence<Character> plain = createSequence("HELLO");
        Sequence<Character> plainCopy = createSequence("HELLO");

        Sequence<Character> encrypted = cipher.encrypt(plain);

        assertEquals("RIJVS", sequenceToString(encrypted));
        assertEquals(plainCopy, plain); // plain unchanged
        assertEquals("KEY", sequenceToString(cipher.key())); // key unchanged
    }

    @Test
    public void testEncryptWithSpaces() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> key = createSequence("KEY");
        cipher.setKey(key);

        Sequence<Character> plain = createSequence("HELLO WORLD");
        Sequence<Character> plainCopy = createSequence("HELLO WORLD");

        Sequence<Character> encrypted = cipher.encrypt(plain);

        assertEquals("RIJVS UYVJN", sequenceToString(encrypted));
        assertEquals(plainCopy, plain);
    }

    @Test
    public void testEncryptLowercase() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> key = createSequence("KEY");
        cipher.setKey(key);

        Sequence<Character> plain = createSequence("hello");
        Sequence<Character> plainCopy = createSequence("hello");

        Sequence<Character> encrypted = cipher.encrypt(plain);

        assertEquals("RIJVS", sequenceToString(encrypted));
        assertEquals(plainCopy, plain);
    }

    @Test
    public void testEncryptMixedCase() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> key = createSequence("KEY");
        cipher.setKey(key);

        Sequence<Character> plain = createSequence("HeLLo WoRLd");
        Sequence<Character> plainCopy = createSequence("HeLLo WoRLd");

        Sequence<Character> encrypted = cipher.encrypt(plain);

        assertEquals("RIJVS UYVJN", sequenceToString(encrypted));
        assertEquals(plainCopy, plain);
    }

    @Test
    public void testEncryptWithNumbers() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> key = createSequence("KEY");
        cipher.setKey(key);

        Sequence<Character> plain = createSequence("HELLO123");
        Sequence<Character> plainCopy = createSequence("HELLO123");

        Sequence<Character> encrypted = cipher.encrypt(plain);

        assertEquals("RIJVS123", sequenceToString(encrypted));
        assertEquals(plainCopy, plain);
    }

    @Test
    public void testEncryptWithPunctuation() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> key = createSequence("KEY");
        cipher.setKey(key);

        Sequence<Character> plain = createSequence("HELLO, WORLD!");
        Sequence<Character> plainCopy = createSequence("HELLO, WORLD!");

        Sequence<Character> encrypted = cipher.encrypt(plain);

        assertEquals("RIJVS, UYVJN!", sequenceToString(encrypted));
        assertEquals(plainCopy, plain);
    }

    @Test
    public void testEncryptKeyWraparound() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> key = createSequence("AB");
        cipher.setKey(key);

        Sequence<Character> plain = createSequence("HELLO");
        Sequence<Character> plainCopy = createSequence("HELLO");

        Sequence<Character> encrypted = cipher.encrypt(plain);

        assertEquals("HFNLP", sequenceToString(encrypted));
        assertEquals(plainCopy, plain);
    }

    /*
     * Test cases for decrypt
     */

    @Test
    public void testDecryptSimple() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> key = createSequence("KEY");
        cipher.setKey(key);

        Sequence<Character> encrypted = createSequence("RIJVS");
        Sequence<Character> encryptedCopy = createSequence("RIJVS");

        Sequence<Character> decrypted = cipher.decrypt(encrypted);

        assertEquals("HELLO", sequenceToString(decrypted));
        assertEquals(encryptedCopy, encrypted); // encrypted unchanged
        assertEquals("KEY", sequenceToString(cipher.key())); // key unchanged
    }

    @Test
    public void testDecryptWithSpaces() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> key = createSequence("KEY");
        cipher.setKey(key);

        Sequence<Character> encrypted = createSequence("RIJVS UYVJN");
        Sequence<Character> encryptedCopy = createSequence("RIJVS UYVJN");

        Sequence<Character> decrypted = cipher.decrypt(encrypted);

        assertEquals("HELLO WORLD", sequenceToString(decrypted));
        assertEquals(encryptedCopy, encrypted);
    }

    @Test
    public void testDecryptWithPunctuation() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> key = createSequence("KEY");
        cipher.setKey(key);

        Sequence<Character> encrypted = createSequence("RIJVS, UYVJN!");
        Sequence<Character> encryptedCopy = createSequence("RIJVS, UYVJN!");

        Sequence<Character> decrypted = cipher.decrypt(encrypted);

        assertEquals("HELLO, WORLD!", sequenceToString(decrypted));
        assertEquals(encryptedCopy, encrypted);
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
        Sequence<Character> originalCopy = createSequence("ATTACK AT DAWN");

        Sequence<Character> encrypted = cipher.encrypt(original);
        Sequence<Character> decrypted = cipher.decrypt(encrypted);

        assertEquals(originalCopy, original);
        assertEquals("ATTACK AT DAWN", sequenceToString(decrypted));
    }

    @Test
    public void testEncryptDecryptEmptySpaces() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> key = createSequence("KEY");
        cipher.setKey(key);

        Sequence<Character> original = createSequence("   ");
        Sequence<Character> originalCopy = createSequence("   ");

        Sequence<Character> encrypted = cipher.encrypt(original);
        Sequence<Character> decrypted = cipher.decrypt(encrypted);

        assertEquals(originalCopy, original);
        assertEquals("   ", sequenceToString(encrypted));
        assertEquals("   ", sequenceToString(decrypted));
    }

    /*
     * Test cases for clear
     */

    @Test
    public void testClearFromEmpty() {
        VigenereCipher cipher = new VigenereCipher1L();
        VigenereCipher cipherExpected = new VigenereCipher1L();

        cipher.clear();

        assertEquals(cipherExpected, cipher);
    }

    @Test
    public void testClearFromNonEmpty() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> key = createSequence("KEY");
        cipher.setKey(key);

        VigenereCipher cipherExpected = new VigenereCipher1L();

        cipher.clear();

        assertEquals(cipherExpected, cipher);
    }

    /*
     * Test cases for newInstance
     */

    @Test
    public void testNewInstanceEmpty() {
        VigenereCipher cipher = new VigenereCipher1L();
        VigenereCipher cipherNew = cipher.newInstance();

        assertEquals(new VigenereCipher1L(), cipherNew);
        assertTrue(cipherNew instanceof VigenereCipher1L);
    }

    @Test
    public void testNewInstanceNonEmpty() {
        VigenereCipher cipher = new VigenereCipher1L();
        Sequence<Character> key = createSequence("KEY");
        cipher.setKey(key);

        VigenereCipher cipherNew = cipher.newInstance();

        assertEquals(new VigenereCipher1L(), cipherNew);
        assertEquals("KEY", sequenceToString(cipher.key())); // original unchanged
    }

    /*
     * Test cases for transferFrom
     */

    @Test
    public void testTransferFromEmpty() {
        VigenereCipher cipher1 = new VigenereCipher1L();
        VigenereCipher cipher2 = new VigenereCipher1L();

        VigenereCipher cipher1Expected = new VigenereCipher1L();
        VigenereCipher cipher2Expected = new VigenereCipher1L();

        cipher1.transferFrom(cipher2);

        assertEquals(cipher1Expected, cipher1);
        assertEquals(cipher2Expected, cipher2);
    }

    @Test
    public void testTransferFromNonEmpty() {
        VigenereCipher cipher1 = new VigenereCipher1L();
        VigenereCipher cipher2 = new VigenereCipher1L();
        Sequence<Character> key = createSequence("SOURCE");
        cipher2.setKey(key);

        VigenereCipher cipher2Expected = new VigenereCipher1L();

        cipher1.transferFrom(cipher2);

        assertEquals("SOURCE", sequenceToString(cipher1.key()));
        assertEquals(cipher2Expected, cipher2); // source should be empty
    }

    @Test
    public void testTransferFromReplace() {
        VigenereCipher cipher1 = new VigenereCipher1L();
        Sequence<Character> key1 = createSequence("FIRST");
        cipher1.setKey(key1);

        VigenereCipher cipher2 = new VigenereCipher1L();
        Sequence<Character> key2 = createSequence("SECOND");
        cipher2.setKey(key2);

        VigenereCipher cipher2Expected = new VigenereCipher1L();

        cipher1.transferFrom(cipher2);

        assertEquals("SECOND", sequenceToString(cipher1.key()));
        assertEquals(cipher2Expected, cipher2);
    }

}