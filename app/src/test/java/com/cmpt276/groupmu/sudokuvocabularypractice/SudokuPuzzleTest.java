package com.cmpt276.groupmu.sudokuvocabularypractice;

import org.junit.Test;


import java.util.Locale;
import static org.junit.Assert.*;

//@RunWith(JUnit4.class)

/**
 * SudokuPuzzle.java Test Class
 */
public class SudokuPuzzleTest {
    @Test
   public void  testGetWordChoices(){
        SudokuPuzzle testWordChoices = new SudokuPuzzle();
        assertArrayEquals(testWordChoices.frenchWords, testWordChoices.getChoiceWords());
    }


    @Test
    public void testGetWordAtPosition(){
        // Setup variables to ensure that the correct
        SudokuPuzzle foreignWords = new SudokuPuzzle();
        // Loop through puzzle and check that english and french words matchup
        for(int i = 0; i < foreignWords.originalPuzzle.length; i++){
            assertEquals(foreignWords.englishWords[foreignWords.originalPuzzle[i]], foreignWords.getWordAtPosition(i));

        }
    }
    @Test
    public void testGetTranslationAtPosition(){
        // Setup variables to ensure that the correct
        SudokuPuzzle testTranslation = new SudokuPuzzle();
        // Loop through puzzle and check that english and french words matchup
        for(int i = 0; i < testTranslation.originalPuzzle.length; i++){
            assertEquals(testTranslation.frenchWords[testTranslation.originalPuzzle[i]], testTranslation.getTranslationAtPosition(i));

        }
    }
    @Test
    public void testGetColumn() {
        SudokuPuzzle testColumn = new SudokuPuzzle();
        // Manually set first column (assumes 9x9 puzzle size)
        for (int i=0; i<9; i++) {
            testColumn.workingPuzzle[i*9] = i;
        }
        int [] expect = {0,1,2,3,4,5,6,7,8};
        int [] testCol = testColumn.getColumn(0);
        assertArrayEquals(expect, testCol);

    }

    @Test
    public void testIsNotPreset (){
            SudokuPuzzle testPreset = new SudokuPuzzle();
            assertTrue(testPreset.isNotPreset(16));
    }
    @Test
    public void testSetValueAtPosition() {

        SudokuPuzzle testPreset = new SudokuPuzzle();
        testPreset.setValueAtPosition(14, 6);
        int expect = 6;
        assertEquals(expect, testPreset.workingPuzzle[14]);
    }
    @Test
    public void testGetVoiceLocale (){
            SudokuPuzzle testVoice = new SudokuPuzzle();
            Locale expect = Locale.FRENCH;
            Locale actual = testVoice.getVoiceLocale();
            assertEquals(expect,actual);
    }
    @Test
    public void testCheckSudokuIncorrect(){
        SudokuPuzzle testCheckSudoku = new SudokuPuzzle();
        assertTrue(testCheckSudoku.checkSudokuIncorrect());
    }
    @Test
    public void testCheckSudokuIncomplete(){
            SudokuPuzzle testCheckIncomplete = new SudokuPuzzle();

            boolean actual = testCheckIncomplete.checkSudokuIncomplete();
            assertTrue(actual);

            for(int i = 0; i < 81; i++){
                testCheckIncomplete.setValueAtPosition(i, 1);
            }

            assertFalse(testCheckIncomplete.checkSudokuIncomplete());


    }
    @Test
    public void testGetRow (){
            SudokuPuzzle testRow = new SudokuPuzzle();
            // Manually set first row (assumes 9x9 puzzle size)
            for (int i=0; i<9; i++) {
                testRow.workingPuzzle[i] = i;
            }
            int[] expectRow = {0,1,2,3,4,5,6,7,8};
            int[] actualRow = testRow.getRow(0);
            assertArrayEquals(expectRow,actualRow);
    }
    @Test
    public void testGetBox(){
            SudokuPuzzle testBox = new SudokuPuzzle();
            // Manually set the section of the puzzle (relies on 9x9 size)
            for (int i=0; i<9; i++) {
                testBox.workingPuzzle[30 + (i%3) + 9*(i/3)] = i+1;
            }
            int[] expectBox = {1,2,3,4,5,6,7,8,9};
            int[] actualBox = testBox.getBox(4);
            assertArrayEquals(expectBox,actualBox);
    }
    @Test
    public void testContainDuplicates (){
            SudokuPuzzle testPuzzle = new SudokuPuzzle();
            // Zero the array: containsDuplicates should ignore zeros (empty squares)
            for (int i=0; i<testPuzzle.workingPuzzle.length; i++) {
                testPuzzle.workingPuzzle[i] = 0;
            }
            assertFalse(testPuzzle.containsDuplicates(testPuzzle.workingPuzzle));
            // Add duplicates
            testPuzzle.workingPuzzle[0] = 1;
            testPuzzle.workingPuzzle[1] = 1;
            assertTrue(testPuzzle.containsDuplicates(testPuzzle.workingPuzzle));
    }
    @Test
    public void testGetCurrentLanguage(){
            SudokuPuzzle testCurrentLanguage = new SudokuPuzzle();
            String expectName = "English";
            String actualName = testCurrentLanguage.getCurrentLanguage();
            assertEquals(expectName,actualName);
    }
    @Test
    public void testSwapLanguage(){
            SudokuPuzzle testSwap = new SudokuPuzzle();
            int expect = 0;
            testSwap.swapLanguage();
            int actual = testSwap.languageIndex;
            assertEquals(expect, actual);
    }
//    @Test
//    public void testResetPuzzle(){
//            SudokuPuzzle testReset = new SudokuPuzzle();
//            int [] expect = testReset.originalPuzzle;
//            testReset.setValueAtPosition(35,5);
//            testReset.resetPuzzle();
//            int [] actual = testReset.workingPuzzle;
//            assertArrayEquals(expect,actual);
//    }

    @Test
    public void testSwapMode(){
        SudokuPuzzle testMode = new SudokuPuzzle();
        testMode.swapMode();
        assertFalse(testMode.isNormalMode());
        testMode.swapMode();
        assertTrue(testMode.isNormalMode());

    }

    @Test
    public void testGetForeignWordAtPosition(){
        // Setup variables to ensure that the correct
        SudokuPuzzle foreignWords = new SudokuPuzzle();
        // Loop through puzzle and check that english and french words matchup
        for(int i = 0; i < foreignWords.originalPuzzle.length; i++){
            assertEquals(foreignWords.frenchWords[foreignWords.originalPuzzle[i]], foreignWords.getForeignWordAtPosition(i));

        }

    }

    @Test
    public void testGetForeignLanguage(){
        SudokuPuzzle foreignWords = new SudokuPuzzle();
        assertEquals("French", foreignWords.getForeignLanguage());
    }

//    @Test
//    public void testNewPuzzle(){
//        SudokuPuzzle newpuzzle = new SudokuPuzzle();
//        SudokuPuzzle oldpuzzle = new SudokuPuzzle();
//        newpuzzle.newPuzzle();
//
//        assertFalse(Arrays.equals(newpuzzle.workingPuzzle, oldpuzzle.originalPuzzle));
//
//    }



}