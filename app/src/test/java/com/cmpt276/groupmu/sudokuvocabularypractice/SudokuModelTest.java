package com.cmpt276.groupmu.sudokuvocabularypractice;

import org.junit.Test;


import java.util.Arrays;
import java.util.Locale;
import static org.junit.Assert.*;

//@RunWith(JUnit4.class)

/**
 * SudokuModel.java Test Class
 */
public class SudokuModelTest {

    @Test
    public void testGetWordAtPosition(){
        // Setup variables to ensure that the correct
        SudokuModel foreignWords = new SudokuModel(9);
        // Loop through puzzle and check that english and french words matchup
        for(int i = 0; i < foreignWords.originalPuzzle.length; i++){
            assertEquals(foreignWords.englishWords[foreignWords.originalPuzzle[i]], foreignWords.getWordAtPosition(i));

        }
    }

    @Test
    public void testGetTranslationAtPosition(){
        // Setup variables to ensure that the correct
        SudokuModel testTranslation = new SudokuModel(9);
        // Loop through puzzle and check that english and french words matchup
        for(int i = 0; i < testTranslation.originalPuzzle.length; i++){
            assertEquals(testTranslation.frenchWords[testTranslation.originalPuzzle[i]], testTranslation.getTranslationAtPosition(i));

        }
    }


    @Test
    public void testSwapMode(){
        SudokuModel testMode = new SudokuModel(9);
        testMode.swapMode();
        assertFalse(testMode.isNormalMode());
        testMode.swapMode();
        assertTrue(testMode.isNormalMode());

    }

    @Test
    public void testGetForeignWordAtPosition(){
        // Setup variables to ensure that the correct
        SudokuModel foreignWords = new SudokuModel(9);
        // Loop through puzzle and check that english and french words matchup
        for(int i = 0; i < foreignWords.originalPuzzle.length; i++){
            assertEquals(foreignWords.frenchWords[foreignWords.originalPuzzle[i]], foreignWords.getForeignWordAtPosition(i));

        }

    }

    @Test
    public void testNewPuzzle(){
        SudokuModel puzzle = new SudokuModel(9);
        int[] first_originalPuzzle = puzzle.originalPuzzle;
        puzzle.generateNewPuzzle();
        // After generateNewPuzzle(), the originalPuzzle should be different,
        // and it should also be a valid puzzle
        assertFalse(Arrays.equals(puzzle.originalPuzzle, first_originalPuzzle));
        assertFalse(puzzle.checkSudokuIncorrect());
    }

}