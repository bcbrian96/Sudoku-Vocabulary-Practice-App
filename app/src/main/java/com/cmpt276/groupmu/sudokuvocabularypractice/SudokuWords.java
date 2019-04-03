package com.cmpt276.groupmu.sudokuvocabularypractice;

import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import static android.content.ContentValues.TAG;



/**
 * SudokuWords class. Contains methods:
 * - Relating to words/languages of the puzzle
 */
class SudokuWords {

    /** VARIABLES */
    int detected_User_Choice_Size = 9;
    // Can be: 4, 6, 9, 12
    // boxes: 2x2, 2x3, 3x3, 3x4
    int difficulty;

    int languageIndex = 1;
    private String languageNames[] = {"French","English"};
    private Locale locales[] = {Locale.ENGLISH, Locale.FRENCH};
    // Note: languageNames[] is in the opposite order of Words[].
    // Default words (numbers)
    private String[] defaultFrenchWords = {" ", "Un", "Deux", "Trois", "Quatre", "Cinq", "Six", "Sept", "Huit", "Neuf", "Dix", "Onze", "Douze"};
    private String[] defaultEnglishWords = {" ", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve"};
    // All words:
    String[] allFrenchWords = defaultFrenchWords.clone();
    String[] allEnglishWords = defaultEnglishWords.clone();
    // The words used in the visible puzzle:
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
//    private int currentPuzzleIndex = -1;


    /**
     * Gets the list of words for each language (possible inputs).
     * This is for the dialog the user sees when inputting a word.
     * @return  The list of words for the input language.
     */
    String[] getChoiceWords() {
        return Words[languageIndex];
    }

    String[] getPresetWords() {
        return Words[languageIndex^1];
    }

    String[] getForeignWords() {
        return Words[1];
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
     * Load a new set of word pairs for use in a puzzle.
     * Takes pairs from allEnglishWords/allFrenchWords,
     * according to which have the most in numHints.
     * Currently, there is no support for word lists smaller than puzzle size
     * (this may result in an error.)
     */
    void loadWordPairs() {
        int[] newPairIndexes = new int[detected_User_Choice_Size+1];
        int[] tempNumHints = new int[allFrenchWords.length];
        // tempNumHints tracks word pairs in allFrenchWords instead of frenchWords
        // (independent of `detected_User_Choice_Size`)
        for(int i=0; i<numHints.length; i++){
            // if it was *not* a 'padding word' (from defaultWords)
            if(pairIndexes[i] < tempNumHints.length)
                tempNumHints[pairIndexes[i]] = numHints[i];
        }
        newPairIndexes[0] = 0; // Add the empty string.
        tempNumHints[0] = -1; // Ignore the empty string when adding words.
        // add hinted: find top 3 most hinted words, and add them first
        if(allFrenchWords.length < detected_User_Choice_Size+1) {
            //Log.d("newPuzzle","not enough words")
            // add all words
            // set first N words to permutation of all words
            for(int i=1; i<newPairIndexes.length; i++) {
                newPairIndexes[i] = i;
            }
        } else {
            // add first 3 words according to hints
            int i = 1; // position in newPairIndexes
            for(; i<4; i++) {
                int max_j = 1;
                for(int j=2; j<tempNumHints.length; j++) {
                    // change to >= to prefer words at end of list instead of start.
                    if (tempNumHints[j] > tempNumHints[max_j]) {
                        max_j = j;
                    }
                }
                tempNumHints[max_j] = -1; // Make sure we don't choose the same one again.
                newPairIndexes[i] = max_j; // index of the word pair with the most hints.
            }
            // Add the rest of the words
            for(int j=1; i<newPairIndexes.length; i++, j++){
                while(tempNumHints[j] == -1) j++; // ignore already taken pairs
                newPairIndexes[i] = j;
            }
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
        int i=0;
        for(; i<pairIndexes.length && i<allEnglishWords.length; i++){
            englishWords[i] = allEnglishWords[pairIndexes[i]];
            frenchWords[i] = allFrenchWords[pairIndexes[i]];
        }
        // if not enough words in allWords, use from defaultWords
        while(i < pairIndexes.length) {
            englishWords[i] = defaultEnglishWords[pairIndexes[i]];
            frenchWords[i] = defaultFrenchWords[pairIndexes[i]];
            i++;
        }
        Words = new String[][]{englishWords,frenchWords};
    }


    /**
     * Reset hints when making a new puzzle.
     */
    private void resetHints() {
        // Reset the number of hints for each word.
        numHints = new int[detected_User_Choice_Size+1];
    }


}

//END
