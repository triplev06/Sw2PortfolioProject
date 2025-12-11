# Vigenere Cipher Component

A Java implementation of the classic Vigenere cipher, built using OSU's component-based software design principles.

## What is this?

This project implements a Vigenere cipher - a method of encrypting text that was considered unbreakable for centuries! Instead of shifting each letter by the same amount (like a Caesar cipher), the Vigenere cipher uses a keyword to shift each letter by different amounts, making it much more secure.

For example, with the key "KEY":
- "HELLO" encrypts to "RIJVS"
- Spaces and punctuation stay the same
- Everything gets converted to uppercase

## How It Works

The Vigenere cipher works by:
1. Taking a keyword (like "SECRET")
2. Repeating it to match your message length
3. Shifting each letter in your message by the corresponding letter in the key
4. Non-letters (spaces, numbers, punctuation) stay unchanged

Example:
```
Message: HELLO WORLD
Key:     SECRE TSECR
Result:  ZINZC HDVZR
```

## Features

-  Encrypt and decrypt text
-  Works with any letter-based key
-  Preserves spaces, punctuation, and numbers
-  Converts everything to uppercase automatically
-  Can encrypt with temporary keys without changing your stored key
-  Built following proper OSU core software engineering practices

## Project Structure

```
├── src/
│   ├── VigenereCipherKernel.java       # Core operations interface
│   ├── VigenereCipher.java             # Enhanced interface with helper methods
│   ├── VigenereCipherSecondary.java    # Implementation of helper methods
│   └── VigenereCipher1L.java           # Main implementation
├── test/
│   └── VigenereCipher1LTest.java       # JUnit tests
└── lib/
    ├── components.jar                   # OSU component library
    ├── junit-4.13.2.jar                # Testing framework
    └── hamcrest-core-1.3.jar           # Testing utilities
```

## Design Principles

This project follows OSU's component-based software design, which means:

- **Kernel Interface**: Defines the minimal set of operations needed
- **Enhanced Interface**: Adds convenient helper methods
- **Secondary Methods**: Implemented using only kernel methods
- **Concrete Implementation**: Actually stores the data and implements everything

This layered approach makes the code more maintainable and easier to understand.

## What I Learned

Working on this project taught me:
- How to implement classic cryptography algorithms
- The importance of proper software architecture and layered design
- Writing comprehensive test cases with JUnit
- Documentation and design-by-contract principles
- Working with Java's component-based architecture
  
## Known Issues

- Only works with English letters (A-Z)
- All output is uppercase
- Very long keys might be less secure (but that's true of the original Vigenere cipher too)

## Future Improvements

Some things I'd like to add:
- Support for lowercase output
- Handle non-English alphabets
- Add a simple GUI

## Acknowledgments

- Built as part of OSU CSE software components coursework
- Uses the OSU CSE Components library
- Inspired by the historical Vigenere cipher (1553)
- Special thanks to Professor Jeremy Grifski of CSE 2231 (Software 2) for allowing me to create this. I don't think I could have done this without his guidance.

## License

This project was created for educational purposes as part of coursework at The Ohio State University.

## Author

Vikranth Vegesina
