package com.cmpt276.groupmu.sudokuvocabularypractice;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

/**
 * SudokuPuzzle class. Contains methods:
 * - Reading and converting from input streams of sudoku files
 * - Checking and verifying parameters of the sudoku
 */
class SudokuPuzzle {

    /** VARIABLES */
//    9x9
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
    final String[] frenchWords = {"", "Un", "Deux", "Trois", "Quatre", "Cinq", "Six", "Sept", "Huit", "Neuf"};
    final String[] englishWords = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine"};

    String[][] Words = {englishWords, frenchWords};
    private int currentPuzzleIndex = -1;

    // Fancy witchcraft
    enum Mode {NORMAL, LISTENING}
    private Mode mode = Mode.NORMAL;

    /**
     * Reads the puzzles form the sudoku files
     * @param inputStream   Input stream used to read from sudoku files
     */
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

    /**
     * Converts string of puzzles to an array that is useable by the gridView. Sudokus puzzles are
     * sourced from: http://forum.enjoysudoku.com/low-stepper-puzzles-t4200.html
     * @param puzzleString  String of numbers to be converted to an array of integers
     * @return  An array of integers to use in the puzzle gridview to display/organize the words
     */
    private int[] convertPuzzleStringToArray(String puzzleString) {
        // Array to return
        int[] puzzleArray = new int[81];

        // Parse
        int puzzleSize=0;
        for (int j=0; puzzleSize<81 && j<puzzleString.length(); j++) {
            char c = puzzleString.charAt(j);
            if (Character.isDigit(c)) {
                puzzleArray[puzzleSize++] = Character.digit(c,10);
            } else if (c=='.') {
                puzzleArray[puzzleSize++] = 0;
            }
        }

        // Failed: Index out of bounds (too many characters)
        if (puzzleSize < 81) {
            Log.e("Parsing puzzle","Invalid string ("+puzzleSize+" digits)");
//            throw new Exception("Invalid puzzle string");
            return null;
        }
        return puzzleArray;
    }

    /**
     * Check what mode we're in
     * @return  Boolean: true for NORMAL (Reading), false for LISTENING mode
     */
    boolean isNormalMode() {
        return mode==Mode.NORMAL;
    }

    /**
     * Swap the mode from NORMAL (Reading) to LISTENING mode (and vise-versa)
     */
    void swapMode() {
        if (isNormalMode()) {
            mode = Mode.LISTENING;
        } else {
            mode = Mode.NORMAL;
        }
    }

    /**
     * Gets the language (French or English)
     * @return  The language as a string
     */
    String[] getChoiceWords() {
        return Words[languageIndex];
    }

    /**
     * Gets the word at the position of the array for the foreign language.
     * @param position  The position within the sudoku puzzle array
     * @return  The word as a string
     */
    String getForeignWordAtPosition(int position) {
        return Words[1][workingPuzzle[position]];
    }

    /**
     * Gets the word at the position of the array for the specified language. For preset values in
     * one language, and the other language that the user inputs.
     * @param position  The position within the sudoku puzzle array
     * @return  The word as a string
     */
    String getWordAtPosition(int position) {
        if (isNotPreset(position)) {
            return Words[languageIndex][workingPuzzle[position]];
        }
        return Words[languageIndex^1][workingPuzzle[position]];
    }

    /**
     * Gets the translation for a word in the GridView
     * @param position  The position within the sudoku puzzle array
     * @return  The translation of the word as a string at the given position
     */
    String getTranslationAtPosition(int position) {
        if (isNotPreset(position)) {
            return Words[languageIndex^1][workingPuzzle[position]];
        }
        return Words[languageIndex][workingPuzzle[position]];
    }

    /**
     * Sets the value at the position in the Gridview
     * @param position  The position within the sudoku puzzle array
     * @param value     The value to set the puzzle index to
     */
    void setValueAtPosition(int position, int value) {
        //assert (0<=value) && (value<=9);
        workingPuzzle[position] = value;
    }

    /**
     * Checks if the position within the sudoku is not preset (not set to 0)
     * @param position  The position within the sudoku puzzle array
     * @return      True if the position is not preset, false otherwise
     */
    boolean isNotPreset(int position) {
        return originalPuzzle[position]==0;
    }

    /**
     * Swaps languages
     */
    void swapLanguage() {
        languageIndex ^= 1;
    }

    /**
     * Gets the local for the puzzle for Text to Speech
     * @return  The local
     */
    Locale getVoiceLocale() {
        return locales[1]; // The foreign language
    }

    /**
     * Gets the current language (English or French)
     * @return  The current language as a string
     */
    String getCurrentLanguage() {
        return languageNames[languageIndex];
    }

    /**
     * Gets the foreign language (French)
     * @return  The foreign language as a string
     */
    String getForeignLanguage() {
        return languageNames[0];
    }

    /**
     * Checks that the puzzleIndex is within the bounds of the puzzle size and then copies the
     * array to set the puzzle
     * @param puzzleIndex   the index to set
     */
    private void setPuzzle(int puzzleIndex) {
        // Check that puzzle index is valid.
        if (puzzleIndex < 0 || puzzleIndex > allPuzzles.size()) {
            Log.e("setPuzzle","puzzleIndex "+puzzleIndex+" invalid");
            return;
        }
        System.arraycopy(allPuzzles.get(currentPuzzleIndex), 0, originalPuzzle, 0, 81);
        System.arraycopy(originalPuzzle, 0, workingPuzzle, 0, 81);
    }

    /**
     * Checks for a new puzzle
     */
    void newPuzzle() {
        if (allPuzzles.size()==0) {
            Log.d("newPuzzle","No puzzles from file");
            return;
        }

        currentPuzzleIndex = (currentPuzzleIndex + 1) % allPuzzles.size();
        setPuzzle(currentPuzzleIndex);
    }

    /**
     * Reset the puzzle
     */
    void resetPuzzle() {

        for (int i = 0; i < workingPuzzle.length; i++) {
            workingPuzzle[i] = originalPuzzle[i];
      }
    }

    /**
     * Check if the puzzle is correct thus far
     * @return  A boolean value of true if the puzzle is correct so far, false otherwise
     */
    boolean checkSudokuCorrect() {
        boolean result = true;
        for (int regionNum = 0; regionNum < 9; regionNum++) {
            result = result && !containsDuplicates(getRow(regionNum));
            result = result && !containsDuplicates(getColumn(regionNum));
            result = result && !containsDuplicates(getBox(regionNum));
        }
        return result;
    }

    /**
     * Checks if the sudoku is complete
     * @return  Returns true if the puzzle if incomplete, flase otherwise
     */
    boolean checkSudokuIncomplete() {
        for (int value : workingPuzzle) {
            if (value == 0) return true; // Incomplete
        }
        return false; // Puzzle is complete
    }

    /**
     * Gets the row of the sudoku
     * @param rowNum    Row number of the sudoku
     * @return  An array of integers for the puzzle row
     */
    int[] getRow(int rowNum) {
        int[] row = new int[9];
        for (int i = 0; i < 9; i++) {
            row[i] = workingPuzzle[(i + rowNum * 9)];
        }
        return row;
    }

    /**
     * Gets the column of the sudoku
     * @param columnNum Column number of the sudoku
     * @return  An array of integers for the puzzle column
     */
    int[] getColumn(int columnNum) {
        int[] column = new int[9];
        for (int i = 0; i < 9; i++) {
            column[i] = workingPuzzle[(columnNum + i * 9)];
        }
        return column;
    }

    /**
     * Gets the 3x3 box for a specific subset within sudoku. Each 3x3 box should only contain one of
     * each number from 0-9
     * @param boxNum    Box num from 0 - 8
     * @return  The box array of integers
     */
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

    /**
     * Checks if the sudoku region contains duplicates
     * @param region The region being checked within the sudoku
     * @return  A boolean value: true if there contains duplicates, false otherwise
     */
    boolean containsDuplicates(int[] region) {
        boolean[] seen_yet = new boolean[10];
        for (int value : region) {
            if (seen_yet[value]) {
                return true; // we already saw this word
            }
            seen_yet[value] = true;

        }
        return false;
    }



}

//END
