# VigenereCipher Component

## Author

Vikranth Vegesina

## Overview

This folder contains the implemntation of the `VigenereCipher` component following OSU CSE component design principles. The VigenereCipher component handles encryption and decryption of text using the Vigenere cipher algorithm.

## Component Structure

Followig assignment by Prof Grifski, this component consists of four Java files:

### 1. **VigenereCipherKernel.java** (Kernel Interface)
- Extends `Standard<VigenereCipher>`
- Defines the core operations:
  - `encrypt(Sequence<Character> text)`: Encrypts text using the stred key
  - `decrypt(Sequence<Character> text)`: Decrypts text using the stored key
  - `key()`: Returns a copy of the current key
  - `setKey(Sequence<Character> key)`: Sets the encryption key

### 2. **VigenereCipher.java** (Enhanced Interface)
- Extends `VigenereCipherKernel`
- Adds secondary methods:
  - `encryptWithKey(Sequence<Character> text, Sequence<Character> key)`: Encrypts with a specific key without changing the stored key
  - `decryptWithKey(Sequence<Character> text, Sequence<Character> key)`: Decrypts with a specific key without changing the stored key
  - `isValidKey()`: Checks if the stored key is valid (non-empty, all letters)
  - `setKeyFromString(String keyStr)`: Sets key from a String
  - `keyToString()`: Returns the key as a String
  - `stringToSequence(String s)`: Converts String to Sequence<Character>
  - `sequenceToString(Sequence<Character> seq)`: Converts Sequence<Character> to String

### 3. **VigenereCipherSecondary.java** (Abstract Secondary Class)
- Implements `VigenereCipher`
- Provides layered implementations of secondary methods using kernel methods
- Implements `equals()`, `hashCode()`, and `toString()` from Object
- All secondary methods are implemented using only kernel methods and other secondary methods

### 4. **VigenereCipher1L.java** (Kernel Implementation)
- Extends `VigenereCipherSecondary`
- Implements all kernel methods
- Uses `Sequence<Character>` to represent the key
- Implements standard methods: `clear()`, `newInstance()`, `transferFrom()`

## Directory Structure

```
src/
└── components/
    └── vigenerecipher/
        ├── VigenereCipherKernel.java      (Kernel Interface)
        ├── VigenereCipher.java             (Enhanced Interface)
        ├── VigenereCipherSecondary.java    (Secondary Methods)
        └── VigenereCipher1L.java           (Kernel Implementation)
```

### Vigenere Cipher Algorithm

The Vigenere cipher shifts each letter in the plaintext by the corresponding letter in the key:
- For encryption: `C[i] = (P[i] + K[i mod |K|]) mod 26`
- For decryption: `P[i] = (C[i] - K[i mod |K|] + 26) mod 26`

Where:
- P = plaintext character position (A=0, B=1, ..., Z=25)
- C = ciphertext character position
- K = key character position
- |K| = length of the key

Non-letter charactrs (spaces, punctuation, numbers) are preserved unchanged.

## Notes

- The key is automatically converted to uppercase for consistency
- Only letters in the text are encrypted; spaces, numbers, and punctuation pass through unchanged
- The cipher preserves the case of letters in the original text
- All kernel methods maintain representation invariants and correspondence

