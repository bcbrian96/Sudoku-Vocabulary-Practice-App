package com.cmpt276.groupmu.sudokuvocabularypractice;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;


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
    private ArrayList<int[]> allPuzzles = new ArrayList<>();

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
    private int currentPuzzleIndex = -1;

    void readPuzzlesFromInputStream(InputStream inputStream) {
        // This assumes each puzzle is on a separate line, as in .sdm format.
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = br.readLine()) != null) {
                int[] arr = convertPuzzleStringToArray(line);
                if (arr!=null) allPuzzles.add(arr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    Puzzles are sourced from http://forum.enjoysudoku.com/low-stepper-puzzles-t4200.html
    private int[] convertPuzzleStringToArray(String puzzleString) {
//        if (puzzleString.startsWith("#")) return null;
        int[] puzzleArray = new int[81];
        int puzzleSize=0;
        for (int j=0; puzzleSize<81 && j<puzzleString.length(); j++) {
            char c = puzzleString.charAt(j);
            if (Character.isDigit(c)) {
                puzzleArray[puzzleSize++] = Character.digit(c,10);
            } else if (c=='.') {
                puzzleArray[puzzleSize++] = 0;
            }
        }
        if (puzzleSize < 81) {
            Log.e("Parsing puzzle","Invalid string ("+puzzleSize+" digits)");
//            throw new Exception("Invalid puzzle string");
            return null;
        }
        return puzzleArray;
    }

    SudokuPuzzle() {
    }

    enum Mode {NORMAL, LISTENING}
    private Mode mode = Mode.NORMAL;

    boolean isNormalMode() {
        return mode==Mode.NORMAL;
    }

    void swapMode() {
        if (isNormalMode()) {
            mode = Mode.LISTENING;
        } else {
            mode = Mode.NORMAL;
        }
    }

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

    private void setPuzzle(int puzzleIndex) {
        // Check that puzzle index is valid.
        if (puzzleIndex < 0 || puzzleIndex > allPuzzles.size()) {
            Log.e("setPuzzle","puzzleIndex "+puzzleIndex+" invalid");
            return;
        }
        System.arraycopy(allPuzzles.get(currentPuzzleIndex), 0, originalPuzzle, 0, 81);
        System.arraycopy(originalPuzzle, 0, workingPuzzle, 0, 81);
    }

    void newPuzzle() {
        if (allPuzzles.size()==0) {
            Log.d("newPuzzle","No puzzles from file");
            return;
        }
//        Random random = new Random();
//        currentPuzzleIndex = random.nextInt(allPuzzles.size());
        currentPuzzleIndex = (currentPuzzleIndex + 1) % allPuzzles.size();
        setPuzzle(currentPuzzleIndex);
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

    int[] getRow(int rowNum) {
        int[] row = new int[9];
        for (int i = 0; i < 9; i++) {
            row[i] = workingPuzzle[(i + rowNum * 9)];
        }
        return row;
    }

    int[] getColumn(int columnNum) {
        int[] column = new int[9];
        for (int i = 0; i < 9; i++) {
            column[i] = workingPuzzle[(columnNum + i * 9)];
        }
        return column;
    }

    int[] getBox(int boxNum) {
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
    boolean containsDuplicates(int[] region) {
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
