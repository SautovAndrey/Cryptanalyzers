package com.example.cryptanalyzer;

import java.util.*;

public class StatisticalAnalyzer {

    // ���������� ��������� ������� �������� ��� ������� ������
    public static Map<Character, Integer> analyze(String text) {
        text = text.toLowerCase();  // ���������� ������ � ������� ��������
        Map<Character, Integer> frequencies = new HashMap<>();
        for (char c : text.toCharArray()) {
            frequencies.put(c, frequencies.getOrDefault(c, 0) + 1);
        }
        return frequencies;
    }

    public static String decryptUsingStatisticalAnalysis(String encryptedText, Map<Character, Integer> referenceFrequencies) {
        encryptedText = encryptedText.toLowerCase();  // ���������� ������ � ������� ��������

        Map<Character, Integer> encryptedTextFrequencies = analyze(encryptedText);

        List<Map.Entry<Character, Integer>> referenceList = new ArrayList<>(referenceFrequencies.entrySet());
        referenceList.sort(Map.Entry.comparingByValue());

        List<Map.Entry<Character, Integer>> encryptedList = new ArrayList<>(encryptedTextFrequencies.entrySet());
        encryptedList.sort(Map.Entry.comparingByValue());

        Map<Character, Character> charMapping = new HashMap<>();
        for (int i = 0; i < encryptedList.size() && i < referenceList.size(); i++) {
            charMapping.put(encryptedList.get(i).getKey(), referenceList.get(i).getKey());
        }

        char mostCommonReferenceChar = referenceList.get(referenceList.size() - 1).getKey();  // ����� ����� ������������� ������ � ����������� ������

        StringBuilder decryptedText = new StringBuilder();
        for (char c : encryptedText.toCharArray()) {
            decryptedText.append(charMapping.getOrDefault(c, mostCommonReferenceChar));  // ���� ������� ��� � ����� �����������, �������� ��� �� ����� ����� ������������� ������
        }

        return decryptedText.toString();
    }
}
