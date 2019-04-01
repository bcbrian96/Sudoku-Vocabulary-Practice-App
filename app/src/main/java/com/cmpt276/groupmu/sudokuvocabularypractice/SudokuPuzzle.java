package com.cmpt276.groupmu.sudokuvocabularypractice;

import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;

import static android.content.ContentValues.TAG;



/**
 * SudokuPuzzle class. Contains methods:
 * - Reading and converting from input streams of sudoku files
 * - Checking and verifying parameters of the sudoku
 */
class SudokuPuzzle {

    /** VARIABLES */
//    9x9
    int[] originalPuzzle = {
            5, 4, 0,  0, 7, 0,  0, 0, 0,
            6, 0, 0,  1, 8, 5,  0, 0, 0,
            0, 8, 8,  0, 0, 0,  0, 6, 0,

            8, 0, 0,  0, 6, 0,  0, 0, 3,
            4, 0, 0,  8, 0, 3,  0, 0, 1,
            7, 0, 3,  0, 2, 0,  0, 0, 6,

            0, 6, 0,  0, 0, 0,  0, 8, 0,
            2, 0, 0,  4, 1, 8,  0, 0, 5,
            0, 4, 5,  0, 8, 0,  0, 7, 8
    };
    String[] gridSizeArray= {"4 x 4", "6 x 6","9 x 9", "12 x 12"};


    int detected_User_Choice_Size = 9;
    // Can be: 4, 6, 9, 12
    // boxes: 2x2, 2x3, 3x3, 3x4
    private int boxHeight = 3;
    private int boxWidth = 3;
    // boxHeight * boxWidth == size must be true
    int[] workingPuzzle = originalPuzzle.clone();
    int[] solutionPuzzle;
    int difficulty;

    private ArrayList<int[]> allPuzzles = new ArrayList<>();


    int languageIndex = 1;
    private String languageNames[] = {"French","English"};
    private Locale locales[] = {Locale.ENGLISH, Locale.FRENCH};
    // Note: languageNames[] is in the opposite order of Words[].
    // Default words (numbers)
    String[] defaultFrenchWords = {" ", "Un", "Deux", "Trois", "Quatre", "Cinq", "Six", "Sept", "Huit", "Neuf", "Dix", "Onze", "Douze"};
    String[] defaultEnglishWords = {" ", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve"};
    // temporarily store words loaded from file:
    // use in puzzle:
    String[] allFrenchWords = defaultFrenchWords.clone();
    String[] allEnglishWords = defaultEnglishWords.clone();
    String[] frenchWords = Arrays.copyOfRange(allFrenchWords,0,detected_User_Choice_Size+2);
    String[] englishWords = Arrays.copyOfRange(allEnglishWords,0,detected_User_Choice_Size+2);

    String[][] Words = {englishWords, frenchWords};

    /**
     * numHints stores the number of times a hint was asked for each pair.
     */
    int[] numHints = new int[detected_User_Choice_Size+1];
    /**
     * pairIndexes stores the original position of the puzzle words
     * (eg. englishWords) in the array of all words (eg. allEnglishWords).
     * It's also used with numHints.
     */
    int[] pairIndexes = {0,1,2,3,4,5,6,7,8,9};
    private int currentPuzzleIndex = -1;

    void setPuzzleSize (int gridScale){
        difficulty = (gridScale*gridScale)/3;
        workingPuzzle = new int[gridScale*gridScale];
        solutionPuzzle = new int [gridScale*gridScale];
        originalPuzzle = new int [gridScale*gridScale];
        Log.d(TAG, "setOriginalPuzzle: "+gridScale);
        detected_User_Choice_Size = gridScale;
        switch (gridScale) {
            case 4:
                boxWidth = 2;
                break;
            case 6:
            case 9:
                boxWidth = 3;
                break;
            case 12:
                boxWidth = 4;
                break;
        }
        boxHeight = gridScale / boxWidth;
        //setEnglishWordsAndFrenchWords();
        newPuzzle();
        Log.d("wantSize", "the size:" + detected_User_Choice_Size);
    }
    void getGamePuzzle(int [] inputPuzzle){
        System.arraycopy(inputPuzzle, 0, originalPuzzle, 0, detected_User_Choice_Size * detected_User_Choice_Size);
    }
    void getSolutionPuzzle (int [] inputPuzzle){
        System.arraycopy(inputPuzzle, 0, solutionPuzzle, 0, detected_User_Choice_Size * detected_User_Choice_Size);
    }
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
     * Create a new random puzzle from a SudokuGenerator
     * Also generate new list of word pairs from current pairs.
     */
    void newPuzzle() {
        // generate Puzzle
        SudokuGenerator scalable = new SudokuGenerator(detected_User_Choice_Size,difficulty);
        scalable.generatePuzzle();
        scalable.scalablePuzzleGenerator();
        getGamePuzzle(scalable.gamePuzzle);
        workingPuzzle = originalPuzzle.clone();
        getSolutionPuzzle(scalable.solutionPuzzle);
        // get Words for puzzle
        loadWordPairs();
    }

    /**
     * Load a new set of word pairs for use in a puzzle.
     * Takes pairs from allEnglishWords/allFrenchWords,
     * according to which have the most in numHints.
     */
    private void loadWordPairs() {
        int[] newPairIndexes = new int[detected_User_Choice_Size+1];
        int[] tempNumHints = new int[frenchWords.length];
        // tempNumHints tracks word pairs in allEnglishWords instead of englishWords
        for(int i=0; i<numHints.length; i++){
            tempNumHints[pairIndexes[i]] = numHints[i];
        }
        newPairIndexes[0] = 0; // empty string.
        // add hinted: find top 3 most hinted words, and add them first
        for(int i=0; i<3; i++) {
            int max_j = 0;
            for(int j=1; j<tempNumHints.length; j++) {
                if (tempNumHints[j] > tempNumHints[max_j]) {
                    max_j = j;
                }
            }
            tempNumHints[max_j] = -1; // Make sure we don't choose the same one again.
            newPairIndexes[i+1] = max_j; // index of the word pair with the most hints.
        }
        // Add the rest of the words
        for(int i=3, j=1; i<detected_User_Choice_Size; i++, j++){
            while(tempNumHints[j] == -1) j++; // ignore already taken pairs
            newPairIndexes[i+1] = j;
        }
        pairIndexes = newPairIndexes;
        generatePuzzleWordlist();
        resetHints();
    }

    /**
     * Set englishWords/frenchWords (the words used in the puzzle)
     * according to allEnglishWords/... and pairIndexes.
     */
    void generatePuzzleWordlist() {
        englishWords = new String[detected_User_Choice_Size+1];
        frenchWords = new String[detected_User_Choice_Size+1];
        for(int i = 0; i<pairIndexes.length; i++){
            englishWords[i] = allEnglishWords[pairIndexes[i]];
            frenchWords[i] = allFrenchWords[pairIndexes[i]];
        }
        Words = new String[][]{englishWords,frenchWords};
    }

    /**
     * Reset the puzzle
     */
    void resetPuzzle() {

//        for (int i = 0; i < workingPuzzle.length; i++) {
//            workingPuzzle[i] = originalPuzzle[i];
//        }
        // This is faster
        System.arraycopy(originalPuzzle, 0, workingPuzzle, 0, 81);
    }

    /**
     * Reset hints when making a new puzzle.
     */
    void resetHints() {
        // Reset the number of hints for each word.
        numHints = new int[detected_User_Choice_Size+1];
    }

    /**
     * Check if the puzzle is incorrect thus far
     * @return  A boolean value of true if the puzzle is incorrect so far, false otherwise
     */
    boolean checkSudokuIncorrect() {
        // If any rows/columns/boxes contain duplicates, sudoku is incorrect: return true.
        boolean result = false;
        for (int regionNum = 0; regionNum < detected_User_Choice_Size; regionNum++) {
            result = result || containsDuplicates(getRow(regionNum));
            result = result || containsDuplicates(getColumn(regionNum));
            result = result || containsDuplicates(getBox(regionNum));
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
        int[] row = new int[detected_User_Choice_Size];
        for (int i = 0; i < detected_User_Choice_Size; i++) {
            row[i] = workingPuzzle[(i + rowNum * detected_User_Choice_Size)];
        }
        return row;
    }

    /**
     * Gets the column of the sudoku
     * @param columnNum Column number of the sudoku
     * @return  An array of integers for the puzzle column
     */
    int[] getColumn(int columnNum) {
        int[] column = new int[detected_User_Choice_Size];
        for (int i = 0; i < detected_User_Choice_Size; i++) {
            column[i] = workingPuzzle[(columnNum + i * detected_User_Choice_Size)];
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
        int[] box = new int[detected_User_Choice_Size];
        int boxesPerRow = detected_User_Choice_Size/boxWidth;
        int firstRow = (boxNum / boxesPerRow) * boxHeight;
        int firstCol = (boxNum % boxesPerRow) * boxWidth;
        // Go through the box, left-to-right top-to-bottom.
        for (int row = 0; row < boxHeight; row++) {
            for (int col = 0; col < boxWidth; col++) {
                int position = (firstCol + col) + (firstRow + row) * detected_User_Choice_Size;
                box[col + boxWidth*row] = workingPuzzle[(position)];
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
        boolean[] seen_yet = new boolean[detected_User_Choice_Size+1];
        for (int value : region) {
            if (value!=0 && seen_yet[value]) {
                return true; // we already saw this word
            }
            seen_yet[value] = true;

        }
        return false;
    }



}

//END
