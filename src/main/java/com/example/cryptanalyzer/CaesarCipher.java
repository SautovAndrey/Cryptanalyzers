package com.example.cryptanalyzer;

public class CaesarCipher {

    private static final String ALPHABET = "אבגדהו¸זחטיךכלםמןנסעףפץצקרשתûü‎‏ÿ";
    private static final int ALPHABET_SIZE = ALPHABET.length();

    public static String encrypt(String text, int shift) {
        return process(text, shift);
    }

    public static String decrypt(String text, int shift) {
        return process(text, -shift);
    }

    private static String process(String text, int shift) {
        StringBuilder result = new StringBuilder();
        for (char character : text.toCharArray()) {
            if (Character.isWhitespace(character) || !ALPHABET.contains(Character.toString(character).toLowerCase())) {
                result.append(character);
            } else {
                boolean isUpperCase = Character.isUpperCase(character);
                character = Character.toLowerCase(character);
                int index = ALPHABET.indexOf(character);
                int shiftedIndex = (index + shift + ALPHABET_SIZE) % ALPHABET_SIZE;
                if (isUpperCase) {
                    result.append(Character.toUpperCase(ALPHABET.charAt(shiftedIndex)));
                } else {
                    result.append(ALPHABET.charAt(shiftedIndex));
                }
            }
        }
        return result.toString();
    }
}
