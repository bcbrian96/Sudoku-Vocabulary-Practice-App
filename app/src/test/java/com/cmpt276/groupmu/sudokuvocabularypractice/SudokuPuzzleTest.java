package com.cmpt276.groupmu.sudokuvocabularypractice;

import android.icu.lang.UScript;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.util.Locale;

import static org.junit.Assert.*;

//@RunWith(JUnit4.class)
public class SudokuPuzzleTest {
    @Test
   public void  testGetWordChoices()
    { String [] expect  = {"", "Un", "Deux", "Trois", "Quatre", "Cinq", "Six", "Sept", "Huit", "Neuf"};
        SudokuPuzzle testWordChoices = new SudokuPuzzle();
        String[] testString = testWordChoices.getChoiceWords();
        assertArrayEquals(expect, testString);
    }


    @Test
    public void testGetWordAtPosition(){
        // Setup variables to ensure that the correct
        SudokuPuzzle foreignWords = new SudokuPuzzle();
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

        // Initialize variables
        final String[] frenchWords = {"", "Un", "Deux", "Trois", "Quatre", "Cinq", "Six", "Sept", "Huit", "Neuf"};
        final String[] englishWords = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine"};
        String[][] Words = {englishWords, frenchWords};
        // Loop through puzzle and check that english and french words matchup
        for(int i = 0; i < originalPuzzle.length; i++){
            assertEquals(englishWords[originalPuzzle[i]], foreignWords.getWordAtPosition(i));
            assertEquals(frenchWords[originalPuzzle[i]], foreignWords.getTranslationAtPosition(i));
        }
    }
    @Test
    public void testGetTranslationAtPosition(){
            SudokuPuzzle testTranslation = new SudokuPuzzle();
            String expect = "Huit";
            String actual = testTranslation.getTranslationAtPosition(39);
            assertEquals(expect,actual);
    }
    @Test
    public void testGetColumn() {
        SudokuPuzzle testColumn = new SudokuPuzzle();
        int [] testCol = testColumn.getColumn(4);
        int [] expect = {7,9,0,6,0,2,0,1,8};
        assertArrayEquals(expect, testCol);

    }

    @Test
    public void testIsNotPreset (){
            SudokuPuzzle testPreset = new SudokuPuzzle();
            boolean expect = true;
            boolean actual = testPreset.isNotPreset(16);
            assertEquals(expect, actual);
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
    public void testCheckSudokuCorrect(){
        SudokuPuzzle testCheckSudoku = new SudokuPuzzle();
        boolean expect = false;
        boolean acutal = testCheckSudoku.checkSudokuCorrect();
        assertEquals(expect,acutal);
    }
    @Test
    public void testCheckSudokuIncomplete(){
            SudokuPuzzle testCheckIncomplete = new SudokuPuzzle();
            boolean expect = true;
            boolean actual = testCheckIncomplete.checkSudokuIncomplete();
            assertEquals(expect,actual);
    }
    @Test
    public void testGetRow (){
            SudokuPuzzle testRow = new SudokuPuzzle();
            int[] expectRow = {2, 0, 0,  4, 1, 9,  0, 0, 5};
            int[] actualRow = testRow.getRow(7);
            assertArrayEquals(expectRow,actualRow);
    }
    @Test
    public void testGetBox(){
            SudokuPuzzle testBox = new SudokuPuzzle();
            int[] expectBox = {0,6,0,8,0,3,0,2,0};
            int[] actualBox = testBox.getBox(4);
            assertArrayEquals(expectBox,actualBox);
    }
    @Test
    public void testContainDuplicates (){
            SudokuPuzzle testContainDup = new SudokuPuzzle();
            boolean expect = true;
            boolean actual = testContainDup.containsDuplicates(testContainDup.workingPuzzle);
            assertEquals(expect,actual);
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
    @Test
    public void testResetPuzzle(){
            SudokuPuzzle testReset = new SudokuPuzzle();
            int [] expect = testReset.originalPuzzle;
            testReset.setValueAtPosition(35,5);
            testReset.resetPuzzle();
            int [] actual = testReset.workingPuzzle;
            assertArrayEquals(expect,actual);
    }

    @Test
    public void testSwapMode(){
        SudokuPuzzle testMode = new SudokuPuzzle();
        testMode.swapMode();
        assertNotEquals(true, testMode.isNormalMode());
        testMode.swapMode();
        assertEquals(true, testMode.isNormalMode());

    }

    @Test
    public void testGetForeignWordAtPosition(){
        // Setup variables to ensure that the correct
        SudokuPuzzle foreignWords = new SudokuPuzzle();
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

        // Initialize variables
        final String[] frenchWords = {"", "Un", "Deux", "Trois", "Quatre", "Cinq", "Six", "Sept", "Huit", "Neuf"};
        final String[] englishWords = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine"};
        String[][] Words = {englishWords, frenchWords};
        // Loop through puzzle and check that english and french words matchup
        for(int i = 0; i < originalPuzzle.length; i++){
            assertEquals(frenchWords[originalPuzzle[i]], foreignWords.getForeignWordAtPosition(i));

        }

    }




}