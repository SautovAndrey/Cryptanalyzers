package com.example.cryptanalyzer;

import java.util.*;

public class StatisticalAnalyzer {

    // Возвращает частотный словарь символов для данного текста
    public static Map<Character, Integer> analyze(String text) {
        text = text.toLowerCase();  // Приведение текста к нижнему регистру
        Map<Character, Integer> frequencies = new HashMap<>();
        for (char c : text.toCharArray()) {
            frequencies.put(c, frequencies.getOrDefault(c, 0) + 1);
        }
        return frequencies;
    }

    public static String decryptUsingStatisticalAnalysis(String encryptedText, Map<Character, Integer> referenceFrequencies) {
        encryptedText = encryptedText.toLowerCase();  // Приведение текста к нижнему регистру

        Map<Character, Integer> encryptedTextFrequencies = analyze(encryptedText);

        List<Map.Entry<Character, Integer>> referenceList = new ArrayList<>(referenceFrequencies.entrySet());
        referenceList.sort(Map.Entry.comparingByValue());

        List<Map.Entry<Character, Integer>> encryptedList = new ArrayList<>(encryptedTextFrequencies.entrySet());
        encryptedList.sort(Map.Entry.comparingByValue());

        Map<Character, Character> charMapping = new HashMap<>();
        for (int i = 0; i < encryptedList.size() && i < referenceList.size(); i++) {
            charMapping.put(encryptedList.get(i).getKey(), referenceList.get(i).getKey());
        }

        char mostCommonReferenceChar = referenceList.get(referenceList.size() - 1).getKey();  // Самый часто встречающийся символ в референсном тексте

        StringBuilder decryptedText = new StringBuilder();
        for (char c : encryptedText.toCharArray()) {
            decryptedText.append(charMapping.getOrDefault(c, mostCommonReferenceChar));  // Если символа нет в нашем отображении, заменяем его на самый часто встречающийся символ
        }

        return decryptedText.toString();
    }
}
