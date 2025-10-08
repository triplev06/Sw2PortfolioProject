package src;

import components.sequence.Sequence;
import components.sequence.Sequence1L;

/**
 * Vigenere Cipher implemented using sequence.
 */
public class VigenereCipher {
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private Sequence<Character> key = new Sequence1L<>();

    // Kernel Methods

    public Sequence<Character> encrypt(Sequence<Character> text) {
        return this.encryptWithKey(text, this.key);
    }

    public Sequence<Character> decrypt(Sequence<Character> text) {
        return this.decryptWithKey(text, this.key);
    }

    public Sequence<Character> key() {
        return this.key.newInstance(); // return copy to preserve encapsulation
    }

    public void setKey(Sequence<Character> key) {
        this.key.transferFrom(key);
        makeUpperCase(this.key);
    }

    // Secondary Methods

    public Sequence<Character> encryptWithKey(Sequence<Character> text, Sequence<Character> key) {

    }

    public Sequence<Character> decryptWithKey(Sequence<Character> text, Sequence<Character> key) {

    }

    public boolean isValidKey() {

    }
    //helper methods

    public static sequence<Character> stringToSequence(String s){

    }

    public static sequence<Character> sequenceToString(String s){

    }



    /*
     * Main method showing the component in action.
     */
    public static void main(String[] args) {
        VigenereCipher cipher = new VigenereCipher();

        // Example setup
        Sequence<Character> keySeq = stringToSequence("KEY");
        cipher.setKey(keySeq);

        Sequence<Character> plainText = stringToSequence("HELLO WORLD");

        // Encrypt
        Sequence<Character> encrypted = cipher.encrypt(plainText);
        System.out.println("Encrypted: " + sequenceToString(encrypted));

        // Decrypt
        Sequence<Character> decrypted = cipher.decrypt(encrypted);
        System.out.println("Decrypted: " + sequenceToString(decrypted));
    }
}
