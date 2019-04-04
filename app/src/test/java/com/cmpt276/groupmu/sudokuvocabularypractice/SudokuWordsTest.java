package com.cmpt276.groupmu.sudokuvocabularypractice;

import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

//@RunWith(JUnit4.class)

/**
 * SudokuWords.java Test Class
 */
public class SudokuWordsTest {
    @Test
    public void  testGetWordChoices(){
        SudokuWords testWordChoices = new SudokuWords(9);
        assertArrayEquals(testWordChoices.frenchWords, testWordChoices.getChoiceWords());
    }

    @Test
    public void testGetVoiceLocale (){
            SudokuWords testVoice = new SudokuWords(9);
            Locale expect = Locale.FRENCH;
            Locale actual = testVoice.getVoiceLocale();
            assertEquals(expect,actual);
    }

    @Test
    public void testGetCurrentLanguage(){
            SudokuWords testCurrentLanguage = new SudokuWords(9);
            String expectName = "English";
            String actualName = testCurrentLanguage.getCurrentLanguage();
            assertEquals(expectName,actualName);
    }

    @Test
    public void testSwapLanguage(){
            SudokuWords testSwap = new SudokuWords(9);
            int expect = 0;
            testSwap.swapLanguage();
            int actual = testSwap.languageIndex;
            assertEquals(expect, actual);
    }

    @Test
    public void testGetForeignLanguage(){
        SudokuWords foreignWords = new SudokuWords(9);
        assertEquals("French", foreignWords.getForeignLanguage());
    }

}