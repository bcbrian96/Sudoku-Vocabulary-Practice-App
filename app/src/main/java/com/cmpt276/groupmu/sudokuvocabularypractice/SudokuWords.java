package com.cmpt276.groupmu.sudokuvocabularypractice;

import java.util.Arrays;
import java.util.Locale;


/**
 * SudokuWords class. Contains methods:
 * - Relating to words/languages of the puzzle
 * - Generating new word list for puzzle from set of all word pairs
 * - Keeping track of hints for different words
 */
class SudokuWords {

    /** VARIABLES */
    int size = 9;
    // Can be: 4, 6, 9, 12
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
    private String[] frenchWords = Arrays.copyOfRange(allFrenchWords, 0, size+2);
    private String[] englishWords = Arrays.copyOfRange(allEnglishWords, 0, size+2);

    private String[][] Words = {englishWords, frenchWords};

    /**
     * numHints stores the number of times a hint was asked for each pair.
     */
    int[] numHints = new int[size+1];
    /**
     * pairIndexes stores the original position of the puzzle words
     * (eg. englishWords) in the array of all words (eg. allEnglishWords).
     * It's also used with numHints.
     */
    int[] pairIndexes = new int[size+1];
//    private int currentPuzzleIndex = -1;

    /**
     * Constructor for SudokuWords. Sets initial size, hints, and word pairs.
     * @param initial_size The initial size of the puzzle == number of words in list.
     */
    SudokuWords(final int initial_size) {
        this.size = initial_size;
        resetHints();
        loadWordPairs();
    }

    /**
     * Gets the list of words for each language (possible inputs).
     * This is for the dialog the user sees when inputting a word.
     * Also used to display words
     * @return  The list of words for the input language.
     */
    String[] getChoiceWords() {
        return Words[languageIndex];
    }

    /**
     * Gets the list of words for preset cells.
     * @return The list of words shown in preset cells.
     */
    String[] getPresetWords() {
        return Words[languageIndex^1];
    }

    /**
     * Get the words in the foreign language for speech (in listening mode).
     * @return The list of words in the foreign language.
     */
    String[] getForeignWords() {
        return Words[1];
    }

    /**
     * Swaps languages
     * Swaps the 'Choice' (input) and 'Preset' words.
     */
    void swapLanguage() {
        languageIndex ^= 1;
    }

    /**
     * Gets the locale for the puzzle for Text to Speech.
     * This is always the locale of the foreign language.
     * @return  The locale
     */
    Locale getVoiceLocale() {
        return locales[1];
    }

    /**
     * Gets the current language. This is the language of the words
     * that are displayed in *Preset* cells.
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

    boolean currentLanguageIsNotForeignLanguage() {
        return !getCurrentLanguage().equals(getForeignLanguage());
    }

    /**
     * Load a new set of word pairs for use in a puzzle.
     * Takes pairs from allEnglishWords/allFrenchWords,
     * according to which have the most in numHints.
     * If word list is smaller than puzzle size, more are added from defaultWords.
     */
    void loadWordPairs() {
        int[] newPairIndexes = new int[size+1];
        int[] tempNumHints = new int[allFrenchWords.length];
        // tempNumHints tracks word pairs in allFrenchWords instead of frenchWords
        // (independent of `size`)
        for(int i=0; i<numHints.length; i++){
            // if it was *not* a 'padding word' (from defaultWords)
            if(pairIndexes[i] < tempNumHints.length)
                tempNumHints[pairIndexes[i]] = numHints[i];
        }
        newPairIndexes[0] = 0; // Add the empty string.
        tempNumHints[0] = -1; // Ignore the empty string when adding words.
        // add hinted: find top 3 most hinted words, and add them first
        if(allFrenchWords.length < size+1) {
            //Log.d("loadWordPairs","not enough words")
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
        englishWords = new String[size+1];
        frenchWords = new String[size+1];
        int i;
        for(i=0; i<pairIndexes.length && i<allEnglishWords.length; i++){
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
        numHints = new int[size+1];
    }


}

//END
