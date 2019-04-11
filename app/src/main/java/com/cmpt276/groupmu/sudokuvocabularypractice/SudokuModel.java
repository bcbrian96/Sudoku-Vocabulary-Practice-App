package com.cmpt276.groupmu.sudokuvocabularypractice;

/**
 * SudokuModel class. Contains methods:
 * - getWordAtPosition family, and other functions using both words and position
 * - Listening mode data and methods
 * - Restoring data from Bundle
 */
class SudokuModel {

    SudokuPuzzle puzzle;
    SudokuWords words;

    int detected_User_Choice_Size;


    // Store Reading and Listening modes.
    enum Mode {NORMAL, LISTENING}
    private Mode mode = Mode.NORMAL;

    /**
     * Constructor for SudokuModel. Initializes puzzle and words.
     * @param initial_size The initial size of the puzzle.
     */
    SudokuModel(final int initial_size) {
        detected_User_Choice_Size = initial_size;
        puzzle = new SudokuPuzzle(initial_size);
        words = new SudokuWords(initial_size);
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
     * Gets the word at the position of the array for the foreign language.
     * @param position  The position within the sudoku puzzle array
     * @return  The word as a string
     */
    String getForeignWordAtPosition(final int position) {
        int wordIndex = puzzle.getValueAt(position);
        return words.getForeignWords()[wordIndex];
    }

    /**
     * Gets the word at the position of the array for the specified language. For preset values in
     * one language, and the other language that the user inputs.
     * @param position  The position within the sudoku puzzle array
     * @return  The word as a string
     */
    String getWordAtPosition(final int position) {
        int wordIndex = puzzle.getValueAt(position);
        if (puzzle.isNotPreset(position)) {
            return words.getChoiceWords()[wordIndex];
        }
        return words.getPresetWords()[wordIndex];
    }

    /**
     * Gets the translation for a word in the GridView (used for hints)
     * @param position  The position within the sudoku puzzle array
     * @return  The translation of the word as a string at the given position
     */
    String getTranslationAtPosition(final int position) {
        int wordIndex = puzzle.getValueAt(position);
        if (puzzle.isNotPreset(position)) {
            return words.getPresetWords()[wordIndex];
        }
        return words.getChoiceWords()[wordIndex];
    }

    /**
     * Gets text that should be displayed on screen at position.
     * Handles differences between Reading and Listening modes.
     * @param position The position within the sudoku puzzle array
     * @return  The text that should be displayed on screen for that cell
     */
    String getDisplayedTextAt(final int position) {
        // Never show text for empty cells (0)
        if (puzzle.getValueAt(position)==0) return "";
        // In reading mode, show words
        else if (isNormalMode()) return getWordAtPosition(position);
        // In listening mode, show numbers
        else return Integer.toString(puzzle.getValueAt(position));
    }

    /**
     * Record that a hint was asked for a position on the grid.
     * @param position  The position within the sudoku puzzle array
     */
    void logHint(final int position) {
        words.numHints[puzzle.getValueAt(position)]++;
    }

    /**
     * Create a new random puzzle from a SudokuGenerator
     * Also generate new list of word pairs from current pairs.
     */
    void newPuzzle() {
        // generate Puzzle
        puzzle.generateNewPuzzle();
        // get Words for puzzle
        words.loadWordPairs();
    }

}

//END
