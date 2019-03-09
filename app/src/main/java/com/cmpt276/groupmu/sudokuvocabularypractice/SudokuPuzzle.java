package com.cmpt276.groupmu.sudokuvocabularypractice;

import java.util.ArrayList;
import java.util.Locale;

class SudokuPuzzle {
    final int[] originalPuzzle = {
            5, 4, 0,  0, 7, 0,  0, 0, 0,
            6, 0, 0,  1, 9, 5,  0, 0, 0,
            0, 9, 8,  0, 0, 0,  0, 6, 0,

            8, 0, 0,  0, 6, 0,  0, 0, 3,
            4, 0, 0,  8, 0, 3,  0, 0, 1,
            7, 0, 3,  0, 2, 0,  0, 0, 6,

            0, 6, 0,  0, 0, 0,  0, 8, 0,
            2, 0, 0,  4, 1, 9,  0, 0, 5,
            0, 4, 5,  0, 8, 0,  0, 7, 9
    };
    //    private final int[] originalPuzzle = {
//            1, 2, 3,  4, 5, 6,  7, 8, 9,
//            4, 5, 6,  7, 8, 9,  1, 2, 3,
//            7, 8, 9,  1, 2, 3,  4, 5, 6,
//
//            2, 3, 4,  5, 6, 7,  8, 9, 1,
//            5, 6, 7,  8, 9, 1,  2, 0, 0,
//            8, 9, 1,  2, 3, 4,  5, 6, 7,
//
//            3, 4, 5,  6, 7, 8,  9, 1, 2,
//            6, 7, 8,  9, 1, 2,  3, 4, 5,
//            9, 1, 2,  3, 4, 5,  6, 7, 8
//    };
    final int[] workingPuzzle = originalPuzzle.clone();

    ArrayList<String> enWords = new ArrayList<>();
    ArrayList<String> frWords = new ArrayList<>();

    String[] english = {"", "", "", "", "", "", "", "", ""};
    String[] french = {"", "", "", "", "", "", "", "", ""};


    int languageIndex = 1;
    private String languageNames[] = {"French","English"};
    private Locale locales[] = {Locale.ENGLISH, Locale.FRENCH};
    // Note: languageNames[] is in the opposite order of Words[].
    final String[] frenchWords = {"", "un", "deux", "trois", "quatre", "cinq", "six", "sept", "huit", "neuf"};
    final String[] englishWords = {"", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
    String[][] Words = {englishWords, frenchWords};


    String[] getChoiceWords() {
        return Words[languageIndex];
    }

    String getWordAtPosition(int position) {
        if (isNotPreset(position)) {
            return Words[languageIndex][workingPuzzle[position]];
        }
        return Words[languageIndex^1][workingPuzzle[position]];
    }

    String getTranslationAtPosition(int position) {
        if (isNotPreset(position)) {
            return Words[languageIndex^1][workingPuzzle[position]];
        }
        return Words[languageIndex][workingPuzzle[position]];
    }

    void setValueAtPosition(int position, int value) {
        //assert (0<=value) && (value<=9);
        workingPuzzle[position] = value;
    }
    boolean isNotPreset(int position) {
        return originalPuzzle[position]==0;
    }

    void swapLanguage() {
        languageIndex ^= 1;
    }

    Locale getVoiceLocale() {
        return locales[languageIndex];
    }

    String getCurrentLanguage() {
        return languageNames[languageIndex];
    }

    // Reset workingPuzzle to originalPuzzle
    void resetPuzzle() {
        for (int i = 0; i < workingPuzzle.length; i++) {
            workingPuzzle[i] = originalPuzzle[i];
        }
    }

    boolean checkSudokuCorrect() {
        boolean result = true;
        for (int regionNum = 0; regionNum < 9; regionNum++) {
            result = result && !containsDuplicates(getRow(regionNum));
            result = result && !containsDuplicates(getColumn(regionNum));
            result = result && !containsDuplicates(getBox(regionNum));
        }
        return result;
    }

    boolean checkSudokuIncomplete() {
        for (int value : workingPuzzle) {
            if (value == 0) return true; // Incomplete
        }
        return false; // Puzzle is complete
    }

    private int[] getRow(int rowNum) {
        int[] row = new int[9];
        for (int i = 0; i < 9; i++) {
            row[i] = workingPuzzle[(i + rowNum * 9)];
        }
        return row;
    }

    private int[] getColumn(int columnNum) {
        int[] column = new int[9];
        for (int i = 0; i < 9; i++) {
            column[i] = workingPuzzle[(columnNum + i * 9)];
        }
        return column;
    }

    private int[] getBox(int boxNum) {
        int[] box = new int[9];
        int firstRow = (boxNum - (boxNum % 3));
        int firstCol = 3 * (boxNum % 3);
        // Go through the box, left-to-right top-to-bottom.
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                int position = (firstCol + col) + (firstRow + row) * 9;
                box[col + 3*row] = workingPuzzle[(position)];
            }
        }
        return box;
    }

    //    Check if rows and columns contain duplicates
    private boolean containsDuplicates(int[] region) {
        boolean[] seen_yet = new boolean[10];
        for (int value : region) {
            if (seen_yet[value]) {
                return true; // we already saw this word
            }
            seen_yet[value] = true;
//            break;
        }
        return false;
    }



}
