package com.cmpt276.groupmu.sudokuvocabularypractice;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
    public void testGetWords(){
        // Test various getWords() functions.
        SudokuWords words = new SudokuWords(9, 1);
        String[] c1 = words.getChoiceWords();
        String[] p1 = words.getPresetWords();
        // Both should have the same length.
        assertEquals(c1.length, p1.length);
        // Initially, the choice words should be the foreign words.
        assertArrayEquals(c1, words.getForeignWords());
        // After language is swapped, Choice and Preset should swap places.
        words.swapLanguage();
        String[] c2 = words.getChoiceWords();
        String[] p2 = words.getPresetWords();
        assertArrayEquals(c1, p2);
        assertArrayEquals(p1, c2);
        // After language is swapped, the foreign words should now be the preset words.
        assertArrayEquals(p2, words.getForeignWords());
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
            SudokuWords testCurrentLanguage = new SudokuWords(9, 1);
            String expectName = "English";
            String actualName = testCurrentLanguage.getPresetLanguage();
            assertEquals(expectName,actualName);
    }

    @Test
    public void testPresetNotForeign(){
        SudokuWords words = new SudokuWords(9,1);
        assertTrue(words.presetLanguageIsNotForeignLanguage());
        words.swapLanguage();
        assertFalse(words.presetLanguageIsNotForeignLanguage());
    }

    @Test
    public void testSwapLanguage(){
        SudokuWords testSwap1 = new SudokuWords(9, 1);
        assertEquals(1, testSwap1.languageIndex);
        testSwap1.swapLanguage();
        assertEquals(0, testSwap1.languageIndex);

        SudokuWords testSwap2 = new SudokuWords(9, 0);
        assertEquals(0, testSwap2.languageIndex);
        testSwap2.swapLanguage();
        assertEquals(1, testSwap2.languageIndex);
    }

    @Test
    public void testGetForeignLanguage(){
        SudokuWords foreignWords = new SudokuWords(9);
        assertEquals("French", foreignWords.getForeignLanguage());
    }

    @Test
    public void testNotEnoughWords(){
        SudokuWords words = new SudokuWords(9);
        // Simulate loading a file with three word pairs.
        words.allEnglishWords = new String[]{"","Red","Green","Blue"};
        words.allFrenchWords = new String[]{"","Rouge","Vert","Bleu"};
        List<String> originalEn = Arrays.asList(words.allEnglishWords);
        List<String> originalFr = Arrays.asList(words.allFrenchWords);
        // Try generate list of 9 word pairs. Should take extra from defaultWords (numbers).
        words.loadWordPairs();
        // Convert the resulting word pairs to List for convenience.
        List<String> enW = Arrays.asList(words.englishWords);
        List<String> frW = Arrays.asList(words.frenchWords);
        // We want the correct number of words.
        assertEquals(words.size+1, enW.size());
        assertEquals(words.size+1, frW.size());
        // We want all words to be different (no duplicates).
        // -> Convert to a set and check size: should be the same if no duplicates.
        assertEquals(words.size+1, new HashSet<>(enW).size());
        assertEquals(words.size+1, new HashSet<>(frW).size());
        // We want all our words to all be in the array.
        assertTrue(enW.containsAll(originalEn));
        assertTrue(frW.containsAll(originalFr));
        // We want the words to match up (in pairs).
        for (int i=1; i<4; i++) {
            assertEquals(enW.indexOf(originalEn.get(i)),
                         frW.indexOf(originalFr.get(i)));
        }
    }

    @Test
    public void testTooManyWords(){
        SudokuWords words = new SudokuWords(4);
        // Incomplete. TODO: add more assertions?
        assertEquals(words.size+1, words.englishWords.length);
    }
}
