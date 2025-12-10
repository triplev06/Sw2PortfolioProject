import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.sequence.Sequence;
import components.sequence.Sequence1L;

/**
 * JUnit test fixture for {@code VigenereCipher}'s kernel and secondary
 * methods.
 *
 * @author Vikranth Vegesina
 */
public abstract class VigenereCipherTest {

    /**
     * Invokes the appropriate {@code VigenereCipher} constructor for the
     * implementation under test and returns the result.
     *
     * @return the new cipher
     * @ensures constructorTest = empty cipher with no key set
     */
    protected abstract VigenereCipher constructorTest();

    /**
     * Invokes the appropriate {@code VigenereCipher} constructor for the
     * reference implementation and returns the result.
     *
     * @return the new cipher
     * @ensures constructorRef = empty cipher with no key set
     */
    protected abstract VigenereCipher constructorRef();

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
    public final void testConstructor() {
        VigenereCipher cipher = this.constructorTest();
        VigenereCipher cipherExpected = this.constructorRef();
        assertEquals(cipherExpected, cipher);
    }

    /*
     * Test cases for setKey (kernel method)
     */

    @Test
    public final void testSetKeySimple() {
        VigenereCipher cipher = this.constructorTest();
        VigenereCipher cipherExpected = this.constructorRef();
        Sequence<Character> key = createSequence("KEY");
        Sequence<Character> keyExpected = createSequence("KEY");
        cipherExpected.setKey(keyExpected);

        cipher.setKey(key);

        assertEquals(cipherExpected, cipher);
    }

    @Test
    public final void testSetKeyLowercase() {
        VigenereCipher cipher = this.constructorTest();
        VigenereCipher cipherExpected = this.constructorRef();
        Sequence<Character> key = createSequence("abc");
        Sequence<Character> keyExpected = createSequence("abc");
        cipherExpected.setKey(keyExpected);

        cipher.setKey(key);

        assertEquals(cipherExpected, cipher);
        assertEquals("ABC", sequenceToString(cipher.key()));
    }

    @Test
    public final void testSetKeyMixedCase() {
        VigenereCipher cipher = this.constructorTest();
        VigenereCipher cipherExpected = this.constructorRef();
        Sequence<Character> key = createSequence("AbCdEf");
        Sequence<Character> keyExpected = createSequence("AbCdEf");
        cipherExpected.setKey(keyExpected);

        cipher.setKey(key);

        assertEquals(cipherExpected, cipher);
        assertEquals("ABCDEF", sequenceToString(cipher.key()));
    }

    @Test
    public final void testSetKeyReplace() {
        VigenereCipher cipher = this.constructorTest();
        VigenereCipher cipherExpected = this.constructorRef();
        Sequence<Character> key1 = createSequence("FIRST");
        Sequence<Character> key1Expected = createSequence("FIRST");
        cipher.setKey(key1);
        cipherExpected.setKey(key1Expected);

        Sequence<Character> key2 = createSequence("SECOND");
        Sequence<Character> key2Expected = createSequence("SECOND");

        cipher.setKey(key2);
        cipherExpected.setKey(key2Expected);

        assertEquals(cipherExpected, cipher);
        assertEquals("SECOND", sequenceToString(cipher.key()));
    }

    /*
     * Test cases for key (kernel method)
     */

    @Test
    public final void testKeyAfterSet() {
        VigenereCipher cipher = this.constructorTest();
        VigenereCipher cipherExpected = this.constructorRef();
        Sequence<Character> key = createSequence("TEST");
        Sequence<Character> keyExpected = createSequence("TEST");
        cipher.setKey(key);
        cipherExpected.setKey(keyExpected);

        Sequence<Character> retrieved = cipher.key();

        assertEquals("TEST", sequenceToString(retrieved));
        assertEquals(cipherExpected, cipher);
    }

    @Test
    public final void testKeyReturnsIndependentCopy() {
        VigenereCipher cipher = this.constructorTest();
        VigenereCipher cipherExpected = this.constructorRef();
        Sequence<Character> key = createSequence("KEY");
        Sequence<Character> keyExpected = createSequence("KEY");
        cipher.setKey(key);
        cipherExpected.setKey(keyExpected);

        Sequence<Character> retrieved = cipher.key();
        retrieved.add(0, 'X');

        assertEquals(cipherExpected, cipher);
        assertEquals("KEY", sequenceToString(cipher.key()));
    }

    /*
     * Test cases for encrypt (kernel method)
     */

    @Test
    public final void testEncryptSimple() {
        VigenereCipher cipher = this.constructorTest();
        VigenereCipher cipherExpected = this.constructorRef();
        Sequence<Character> key = createSequence("KEY");
        Sequence<Character> keyExpected = createSequence("KEY");
        cipher.setKey(key);
        cipherExpected.setKey(keyExpected);

        Sequence<Character> plain = createSequence("HELLO");
        Sequence<Character> plainExpected = createSequence("HELLO");

        Sequence<Character> encrypted = cipher.encrypt(plain);
        Sequence<Character> encryptedExpected = cipherExpected
                .encrypt(plainExpected);

        assertEquals("RIJVS", sequenceToString(encrypted));
        assertEquals(encryptedExpected, encrypted);
        assertEquals(cipherExpected, cipher);
    }

    @Test
    public final void testEncryptWithSpaces() {
        VigenereCipher cipher = this.constructorTest();
        VigenereCipher cipherExpected = this.constructorRef();
        Sequence<Character> key = createSequence("KEY");
        Sequence<Character> keyExpected = createSequence("KEY");
        cipher.setKey(key);
        cipherExpected.setKey(keyExpected);

        Sequence<Character> plain = createSequence("HELLO WORLD");
        Sequence<Character> plainExpected = createSequence("HELLO WORLD");

        Sequence<Character> encrypted = cipher.encrypt(plain);
        Sequence<Character> encryptedExpected = cipherExpected
                .encrypt(plainExpected);

        assertEquals(encryptedExpected, encrypted);
        assertEquals(cipherExpected, cipher);
    }

    @Test
    public final void testEncryptLowercase() {
        VigenereCipher cipher = this.constructorTest();
        VigenereCipher cipherExpected = this.constructorRef();
        Sequence<Character> key = createSequence("KEY");
        Sequence<Character> keyExpected = createSequence("KEY");
        cipher.setKey(key);
        cipherExpected.setKey(keyExpected);

        Sequence<Character> plain = createSequence("hello");
        Sequence<Character> plainExpected = createSequence("hello");

        Sequence<Character> encrypted = cipher.encrypt(plain);
        Sequence<Character> encryptedExpected = cipherExpected
                .encrypt(plainExpected);

        assertEquals("RIJVS", sequenceToString(encrypted));
        assertEquals(encryptedExpected, encrypted);
        assertEquals(cipherExpected, cipher);
    }

    @Test
    public final void testEncryptWithPunctuation() {
        VigenereCipher cipher = this.constructorTest();
        VigenereCipher cipherExpected = this.constructorRef();
        Sequence<Character> key = createSequence("KEY");
        Sequence<Character> keyExpected = createSequence("KEY");
        cipher.setKey(key);
        cipherExpected.setKey(keyExpected);

        Sequence<Character> plain = createSequence("HELLO, WORLD!");
        Sequence<Character> plainExpected = createSequence("HELLO, WORLD!");

        Sequence<Character> encrypted = cipher.encrypt(plain);
        Sequence<Character> encryptedExpected = cipherExpected
                .encrypt(plainExpected);

        assertEquals(encryptedExpected, encrypted);
        assertEquals(cipherExpected, cipher);
    }

    @Test
    public final void testEncryptKeyWraparound() {
        VigenereCipher cipher = this.constructorTest();
        VigenereCipher cipherExpected = this.constructorRef();
        Sequence<Character> key = createSequence("AB");
        Sequence<Character> keyExpected = createSequence("AB");
        cipher.setKey(key);
        cipherExpected.setKey(keyExpected);

        Sequence<Character> plain = createSequence("HELLO");
        Sequence<Character> plainExpected = createSequence("HELLO");

        Sequence<Character> encrypted = cipher.encrypt(plain);
        Sequence<Character> encryptedExpected = cipherExpected
                .encrypt(plainExpected);

        assertEquals("HFNLP", sequenceToString(encrypted));
        assertEquals(encryptedExpected, encrypted);
        assertEquals(cipherExpected, cipher);
    }

    /*
     * Test cases for decrypt (kernel method)
     */

    @Test
    public final void testDecryptSimple() {
        VigenereCipher cipher = this.constructorTest();
        VigenereCipher cipherExpected = this.constructorRef();
        Sequence<Character> key = createSequence("KEY");
        Sequence<Character> keyExpected = createSequence("KEY");
        cipher.setKey(key);
        cipherExpected.setKey(keyExpected);

        Sequence<Character> encrypted = createSequence("RIJVS");
        Sequence<Character> encryptedExpected = createSequence("RIJVS");

        Sequence<Character> decrypted = cipher.decrypt(encrypted);
        Sequence<Character> decryptedExpected = cipherExpected
                .decrypt(encryptedExpected);

        assertEquals("HELLO", sequenceToString(decrypted));
        assertEquals(decryptedExpected, decrypted);
        assertEquals(cipherExpected, cipher);
    }

    @Test
    public final void testDecryptWithSpaces() {
        VigenereCipher cipher = this.constructorTest();
        VigenereCipher cipherExpected = this.constructorRef();
        Sequence<Character> key = createSequence("KEY");
        Sequence<Character> keyExpected = createSequence("KEY");
        cipher.setKey(key);
        cipherExpected.setKey(keyExpected);

        Sequence<Character> encrypted = createSequence("RIJVS UYVJN");
        Sequence<Character> encryptedExpected = createSequence("RIJVS UYVJN");

        Sequence<Character> decrypted = cipher.decrypt(encrypted);
        Sequence<Character> decryptedExpected = cipherExpected
                .decrypt(encryptedExpected);

        assertEquals(decryptedExpected, decrypted);
        assertEquals(cipherExpected, cipher);
    }

    /*
     * Test cases for encrypt/decrypt round trip
     */

    @Test
    public final void testEncryptDecryptRoundTrip() {
        VigenereCipher cipher = this.constructorTest();
        VigenereCipher cipherExpected = this.constructorRef();
        Sequence<Character> key = createSequence("SECRET");
        Sequence<Character> keyExpected = createSequence("SECRET");
        cipher.setKey(key);
        cipherExpected.setKey(keyExpected);

        Sequence<Character> original = createSequence("ATTACK AT DAWN");
        Sequence<Character> originalExpected = createSequence("ATTACK AT DAWN");

        Sequence<Character> encrypted = cipher.encrypt(original);
        Sequence<Character> encryptedExpected = cipherExpected
                .encrypt(originalExpected);
        Sequence<Character> decrypted = cipher.decrypt(encrypted);
        Sequence<Character> decryptedExpected = cipherExpected
                .decrypt(encryptedExpected);

        assertEquals("ATTACK AT DAWN", sequenceToString(decrypted));
        assertEquals(decryptedExpected, decrypted);
        assertEquals(cipherExpected, cipher);
    }

    /*
     * Test cases for clear (Standard method)
     */

    @Test
    public final void testClearFromEmpty() {
        VigenereCipher cipher = this.constructorTest();
        VigenereCipher cipherExpected = this.constructorRef();

        cipher.clear();
        cipherExpected.clear();

        assertEquals(cipherExpected, cipher);
    }

    @Test
    public final void testClearFromNonEmpty() {
        VigenereCipher cipher = this.constructorTest();
        VigenereCipher cipherExpected = this.constructorRef();
        Sequence<Character> key = createSequence("KEY");
        Sequence<Character> keyExpected = createSequence("KEY");
        cipher.setKey(key);
        cipherExpected.setKey(keyExpected);

        cipher.clear();
        cipherExpected.clear();

        assertEquals(cipherExpected, cipher);
    }

    /*
     * Test cases for newInstance (Standard method)
     */

    @Test
    public final void testNewInstanceEmpty() {
        VigenereCipher cipher = this.constructorTest();
        VigenereCipher cipherNew = cipher.newInstance();
        VigenereCipher cipherExpected = this.constructorRef();

        assertEquals(cipherExpected, cipherNew);
    }

    @Test
    public final void testNewInstanceNonEmpty() {
        VigenereCipher cipher = this.constructorTest();
        VigenereCipher cipherExpected = this.constructorRef();
        Sequence<Character> key = createSequence("KEY");
        Sequence<Character> keyExpected = createSequence("KEY");
        cipher.setKey(key);
        cipherExpected.setKey(keyExpected);

        VigenereCipher cipherNew = cipher.newInstance();
        VigenereCipher cipherNewExpected = this.constructorRef();

        assertEquals(cipherNewExpected, cipherNew);
        assertEquals(cipherExpected, cipher);
    }

    /*
     * Test cases for transferFrom (Standard method)
     */

    @Test
    public final void testTransferFromEmpty() {
        VigenereCipher cipher1 = this.constructorTest();
        VigenereCipher cipher2 = this.constructorTest();
        VigenereCipher cipher1Expected = this.constructorRef();
        VigenereCipher cipher2Expected = this.constructorRef();

        cipher1.transferFrom(cipher2);
        cipher1Expected.transferFrom(cipher2Expected);

        assertEquals(cipher1Expected, cipher1);
        assertEquals(cipher2Expected, cipher2);
    }

    @Test
    public final void testTransferFromNonEmpty() {
        VigenereCipher cipher1 = this.constructorTest();
        VigenereCipher cipher2 = this.constructorTest();
        VigenereCipher cipher1Expected = this.constructorRef();
        VigenereCipher cipher2Expected = this.constructorRef();
        Sequence<Character> key = createSequence("SOURCE");
        Sequence<Character> keyExpected = createSequence("SOURCE");
        cipher2.setKey(key);
        cipher2Expected.setKey(keyExpected);

        cipher1.transferFrom(cipher2);
        cipher1Expected.transferFrom(cipher2Expected);

        assertEquals(cipher1Expected, cipher1);
        assertEquals(cipher2Expected, cipher2);
    }

    @Test
    public final void testTransferFromReplace() {
        VigenereCipher cipher1 = this.constructorTest();
        VigenereCipher cipher2 = this.constructorTest();
        VigenereCipher cipher1Expected = this.constructorRef();
        VigenereCipher cipher2Expected = this.constructorRef();
        Sequence<Character> key1 = createSequence("FIRST");
        Sequence<Character> key1Expected = createSequence("FIRST");
        cipher1.setKey(key1);
        cipher1Expected.setKey(key1Expected);
        Sequence<Character> key2 = createSequence("SECOND");
        Sequence<Character> key2Expected = createSequence("SECOND");
        cipher2.setKey(key2);
        cipher2Expected.setKey(key2Expected);

        cipher1.transferFrom(cipher2);
        cipher1Expected.transferFrom(cipher2Expected);

        assertEquals(cipher1Expected, cipher1);
        assertEquals(cipher2Expected, cipher2);
    }

    /*
     * Test cases for encryptWithKey (secondary method)
     */

    @Test
    public final void testEncryptWithKeySimple() {
        VigenereCipher cipher = this.constructorTest();
        VigenereCipher cipherExpected = this.constructorRef();
        Sequence<Character> storedKey = createSequence("ORIGINAL");
        Sequence<Character> storedKeyExpected = createSequence("ORIGINAL");
        cipher.setKey(storedKey);
        cipherExpected.setKey(storedKeyExpected);

        Sequence<Character> text = createSequence("HELLO");
        Sequence<Character> textExpected = createSequence("HELLO");
        Sequence<Character> tempKey = createSequence("TEMP");
        Sequence<Character> tempKeyExpected = createSequence("TEMP");

        Sequence<Character> encrypted = cipher.encryptWithKey(text, tempKey);
        Sequence<Character> encryptedExpected = cipherExpected
                .encryptWithKey(textExpected, tempKeyExpected);

        assertEquals(encryptedExpected, encrypted);
        assertEquals(cipherExpected, cipher);
    }

    @Test
    public final void testEncryptWithKeyDifferentFromStored() {
        VigenereCipher cipher = this.constructorTest();
        VigenereCipher cipherExpected = this.constructorRef();
        Sequence<Character> storedKey = createSequence("KEY");
        Sequence<Character> storedKeyExpected = createSequence("KEY");
        cipher.setKey(storedKey);
        cipherExpected.setKey(storedKeyExpected);

        Sequence<Character> text = createSequence("HELLO");
        Sequence<Character> textExpected = createSequence("HELLO");
        Sequence<Character> tempKey = createSequence("ABC");
        Sequence<Character> tempKeyExpected = createSequence("ABC");

        Sequence<Character> encrypted = cipher.encryptWithKey(text, tempKey);
        Sequence<Character> encryptedExpected = cipherExpected
                .encryptWithKey(textExpected, tempKeyExpected);

        assertEquals(encryptedExpected, encrypted);
        assertEquals(cipherExpected, cipher);
    }

    /*
     * Test cases for decryptWithKey (secondary method)
     */

    @Test
    public final void testDecryptWithKeySimple() {
        VigenereCipher cipher = this.constructorTest();
        VigenereCipher cipherExpected = this.constructorRef();
        Sequence<Character> storedKey = createSequence("ORIGINAL");
        Sequence<Character> storedKeyExpected = createSequence("ORIGINAL");
        cipher.setKey(storedKey);
        cipherExpected.setKey(storedKeyExpected);

        Sequence<Character> encrypted = createSequence("AYHSQ");
        Sequence<Character> encryptedExpected = createSequence("AYHSQ");
        Sequence<Character> tempKey = createSequence("TEMP");
        Sequence<Character> tempKeyExpected = createSequence("TEMP");

        Sequence<Character> decrypted = cipher.decryptWithKey(encrypted,
                tempKey);
        Sequence<Character> decryptedExpected = cipherExpected
                .decryptWithKey(encryptedExpected, tempKeyExpected);

        assertEquals(decryptedExpected, decrypted);
        assertEquals(cipherExpected, cipher);
    }

    /*
     * Test cases for isValidKey (secondary method)
     */

    @Test
    public final void testIsValidKeyEmpty() {
        VigenereCipher cipher = this.constructorTest();
        VigenereCipher cipherExpected = this.constructorRef();

        boolean valid = cipher.isValidKey();
        boolean validExpected = cipherExpected.isValidKey();

        assertEquals(validExpected, valid);
        assertFalse(valid);
        assertEquals(cipherExpected, cipher);
    }

    @Test
    public final void testIsValidKeyLettersOnly() {
        VigenereCipher cipher = this.constructorTest();
        VigenereCipher cipherExpected = this.constructorRef();
        Sequence<Character> key = createSequence("KEY");
        Sequence<Character> keyExpected = createSequence("KEY");
        cipher.setKey(key);
        cipherExpected.setKey(keyExpected);

        boolean valid = cipher.isValidKey();
        boolean validExpected = cipherExpected.isValidKey();

        assertEquals(validExpected, valid);
        assertTrue(valid);
        assertEquals(cipherExpected, cipher);
    }

    /*
     * Test cases for setKeyFromString (secondary method)
     */

    @Test
    public final void testSetKeyFromStringSimple() {
        VigenereCipher cipher = this.constructorTest();
        VigenereCipher cipherExpected = this.constructorRef();

        cipher.setKeyFromString("KEY");
        cipherExpected.setKeyFromString("KEY");

        assertEquals(cipherExpected, cipher);
    }

    @Test
    public final void testSetKeyFromStringLowercase() {
        VigenereCipher cipher = this.constructorTest();
        VigenereCipher cipherExpected = this.constructorRef();

        cipher.setKeyFromString("abc");
        cipherExpected.setKeyFromString("abc");

        assertEquals(cipherExpected, cipher);
        assertEquals("ABC", sequenceToString(cipher.key()));
    }

    /*
     * Test cases for keyToString (secondary method)
     */

    @Test
    public final void testKeyToStringSimple() {
        VigenereCipher cipher = this.constructorTest();
        VigenereCipher cipherExpected = this.constructorRef();
        Sequence<Character> key = createSequence("KEY");
        Sequence<Character> keyExpected = createSequence("KEY");
        cipher.setKey(key);
        cipherExpected.setKey(keyExpected);

        String keyString = cipher.keyToString();
        String keyStringExpected = cipherExpected.keyToString();

        assertEquals(keyStringExpected, keyString);
        assertEquals(cipherExpected, cipher);
    }

    /*
     * Test cases for stringToSequence (secondary method)
     */

    @Test
    public final void testStringToSequenceSimple() {
        VigenereCipher cipher = this.constructorTest();
        VigenereCipher cipherExpected = this.constructorRef();

        Sequence<Character> seq = cipher.stringToSequence("HELLO");
        Sequence<Character> seqExpected = cipherExpected
                .stringToSequence("HELLO");

        assertEquals(seqExpected, seq);
        assertEquals(cipherExpected, cipher);
    }

    @Test
    public final void testStringToSequenceEmpty() {
        VigenereCipher cipher = this.constructorTest();
        VigenereCipher cipherExpected = this.constructorRef();

        Sequence<Character> seq = cipher.stringToSequence("");
        Sequence<Character> seqExpected = cipherExpected.stringToSequence("");

        assertEquals(seqExpected, seq);
        assertEquals(0, seq.length());
        assertEquals(cipherExpected, cipher);
    }

    /*
     * Test cases for sequenceToString (secondary method)
     */

    @Test
    public final void testSequenceToStringSimple() {
        VigenereCipher cipher = this.constructorTest();
        VigenereCipher cipherExpected = this.constructorRef();
        Sequence<Character> seq = createSequence("HELLO");
        Sequence<Character> seqExpected = createSequence("HELLO");

        String result = cipher.sequenceToString(seq);
        String resultExpected = cipherExpected.sequenceToString(seqExpected);

        assertEquals(resultExpected, result);
        assertEquals(cipherExpected, cipher);
    }

}