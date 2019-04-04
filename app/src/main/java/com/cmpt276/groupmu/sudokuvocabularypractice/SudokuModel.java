package com.cmpt276.groupmu.sudokuvocabularypractice;

import android.os.Bundle;
import android.util.Log;

/**
 * SudokuModel class. Contains methods:
 * - getWordAtPosition family, and other functions using both words and position
 * - Listening mode data and methods
 * - Restoring data from Bundle
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

    /**
     * Log the cause of the restore failing.
     * @param missingVar The variable that was null in the bundle.
     */
    private void restoreState_Log_Failed_null(String missingVar) {
        Log.i("restoreState","Could not restore model state: "+missingVar+" was null");
    }

    /**
     * Restores the state from bundle, eg. after a rotation.
     * @param savedState The savedstate bundle, passed from the Activity class
     * @return true if successful; otherwise (if any parameter not set) false.
     */
    boolean restoreState(Bundle savedState) {
        int temp_size = savedState.getInt("gridSize", -1);
        if (temp_size==-1) {
            Log.i("restoreState", "No saved state to restore");
            return false;
        }
        int[] wp = savedState.getIntArray("workingPuzzle");
        int[] op = savedState.getIntArray("originalPuzzle");
        String[] allFw = savedState.getStringArray("frenchWords");
        String[] allEw = savedState.getStringArray("englishWords");
        int[] pairI = savedState.getIntArray("pairIndexes");
        int[] hints = savedState.getIntArray("numHints");
        int lang = savedState.getInt("languageIndex", 1);
        // if any were not restored correctly (null), fail and do not update anything.
        if (wp==null) { restoreState_Log_Failed_null("workingPuzzle"); return false; }
        if (op==null) { restoreState_Log_Failed_null("originalPuzzle"); return false; }
        if (allFw==null) { restoreState_Log_Failed_null("frenchWords"); return false; }
        if (allEw==null) { restoreState_Log_Failed_null("englishWords"); return false; }
        if (pairI==null) { restoreState_Log_Failed_null("pairIndexes"); return false; }
        if (hints==null) { restoreState_Log_Failed_null("numHints"); return false; }
        // All successful
        puzzle.size = words.size = this.detected_User_Choice_Size = temp_size;
        puzzle.workingPuzzle = wp;
        puzzle.originalPuzzle = op;
        words.allEnglishWords = allEw;
        words.allFrenchWords = allFw;
        words.pairIndexes = pairI;
        words.numHints = hints;
        words.languageIndex = lang;
        words.generatePuzzleWordlist();
        return true;
    }

}

//END
