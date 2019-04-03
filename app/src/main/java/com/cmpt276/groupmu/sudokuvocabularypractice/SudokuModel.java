package com.cmpt276.groupmu.sudokuvocabularypractice;

/**
 * SudokuPuzzle class. Contains methods:
 * - Reading and converting from input streams of sudoku files
 * - Checking and verifying parameters of the sudoku
 */
class SudokuModel {

    SudokuPuzzle puzzle = new SudokuPuzzle();
    SudokuWords words = new SudokuWords();

    int detected_User_Choice_Size = 9;
    int difficulty;


    // Store Reading and Listening modes.
    enum Mode {NORMAL, LISTENING}
    private Mode mode = Mode.NORMAL;

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
     * Gets the word at the position of the array for the foreign language.
     * @param position  The position within the sudoku puzzle array
     * @return  The word as a string
     */
    String getForeignWordAtPosition(int position) {
        int wordIndex = puzzle.getValueAt(position);
        return words.getForeignWords()[wordIndex];
    }

    /**
     * Gets the word at the position of the array for the specified language. For preset values in
     * one language, and the other language that the user inputs.
     * @param position  The position within the sudoku puzzle array
     * @return  The word as a string
     */
    String getWordAtPosition(int position) {
        int wordIndex = puzzle.getValueAt(position);
        if (puzzle.isNotPreset(position)) {
            return words.getChoiceWords()[wordIndex];
        }
        return words.getPresetWords()[wordIndex];
    }

    /**
     * Gets the translation for a word in the GridView
     * @param position  The position within the sudoku puzzle array
     * @return  The translation of the word as a string at the given position
     */
    String getTranslationAtPosition(int position) {
        int wordIndex = puzzle.getValueAt(position);
        if (puzzle.isNotPreset(position)) {
            return words.getPresetWords()[wordIndex];
        }
        return words.getChoiceWords()[wordIndex];
    }

    /**
     * Create a new random puzzle from a SudokuGenerator
     * Also generate new list of word pairs from current pairs.
     */
    void newPuzzle() {
        // generate Puzzle
        puzzle.newPuzzle();
        // get Words for puzzle
        words.loadWordPairs();
    }

}

//END
