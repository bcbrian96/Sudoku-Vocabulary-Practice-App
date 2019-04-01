package com.cmpt276.groupmu.sudokuvocabularypractice;

import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import static android.content.ContentValues.TAG;



class SudokuPuzzle {
     int[] originalPuzzle = {
            5, 4, 0,  0, 7, 0,  0, 0, 0,
            6, 0, 0,  1, 8, 5,  0, 0, 0,
            0, 8, 8,  0, 0, 0,  0, 6, 0,

            8, 0, 0,  0, 6, 0,  0, 0, 3,
            4, 0, 0,  8, 0, 3,  0, 0, 1,
            7, 0, 3,  0, 2, 0,  0, 0, 6,

            0, 6, 0,  0, 0, 0,  0, 8, 0,
            2, 0, 0,  4, 1, 8,  0, 0, 5,
            0, 4, 5,  0, 8, 0,  0, 7, 8
    };
    String[] gridSizeArray= {"4 x 4", "6 x 6","9 x 9", "12 x 12"};



    //    private final int[] originalPuzzle = {
//            1, 2, 3,  4, 5, 6,  7, 8, 9,
//            4, 5, 6,  7, 8, 9,  1, 2, 3,
//            7, 8, 9,  1, 2, 3,  4, 5, 6,
//
//            2, 3, 4,  5, 6, 7,  8, 9, 1,
//            5, 6, 7,  8, 9, 1,  2, 0, 0,
//            8, 9, 1,  2, 3, 4,  5, 6, 7,
//
//            3, 4, 5,  6, 7, 8,  9, 1, 2,
//            6, 7, 8,  9, 1, 2,  3, 4, 5,
//            9, 1, 2,  3, 4, 5,  6, 7, 8
//    };

    int detected_User_Choice_Size ;
    int[] workingPuzzle;
    int[] solutionPuzzle;
    private ArrayList<int[]> allPuzzles = new ArrayList<>();

    ArrayList<String> enWords = new ArrayList<>();
    ArrayList<String> frWords = new ArrayList<>();

    String[] english = {"", "", "", "", "", "", "", "", ""};
    String[] french = {"", "", "", "", "", "", "", "", ""};


    int languageIndex = 1;
    private String languageNames[] = {"French","English"};
    private Locale locales[] = {Locale.ENGLISH, Locale.FRENCH};
    // Note: languageNames[] is in the opposite order of Words[].
    String[] frenchWords = { " ","un", "deux", "trois", "quatre", "cinq", "six", "sept", "huit", "neuf","dix","Onze","Douze"};
    String[] newFrenchWordsArray;
    String[] englishWords = { " ","one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten","eleven","twelve"};
    String[] newEnglishWordsArray;
    String[][] Words;
    private int currentPuzzleIndex = -1;
    void setPuzzleSize (int gridScale){
        int defaultGameDifficulty = (int)(gridScale*gridScale)/3;
        workingPuzzle = new int[gridScale*gridScale];
        solutionPuzzle = new int [gridScale*gridScale];
        originalPuzzle = new int [gridScale*gridScale];
        Words =new String[][] {setEnglishWords(gridScale),setFrenchWords(gridScale)};
        SudokuGenerator scalable = new SudokuGenerator(gridScale,defaultGameDifficulty);
        Log.d(TAG, "setOriginalPuzzle: "+gridScale);
        switch (gridScale) {
            case 4:
                 {
                    detected_User_Choice_Size = gridScale;
                    // setEnglishWordsAndFrenchWords();
                    scalable.generatePuzzle();
                    scalable.scalablePuzzleGenerator();
                    getGamePuzzle(scalable.gamePuzzle);
                     workingPuzzle = originalPuzzle.clone();
                    getSolutionPuzzle(scalable.solutionPuzzle);
                    Log.d("okok", "Ok, the case actually goes to here");
                    Log.d("wantSize", "the size:" + detected_User_Choice_Size);

                }
                break;
            case 6:
                 {
                     detected_User_Choice_Size = gridScale;
                     //setEnglishWordsAndFrenchWords();
                     scalable.generatePuzzle();
                     scalable.scalablePuzzleGenerator();
                     getGamePuzzle(scalable.gamePuzzle);
                     workingPuzzle = originalPuzzle.clone();
                     getSolutionPuzzle(scalable.solutionPuzzle);
                    Log.d("okok", "Ok?");
                    Log.d("wantSize", "the size:" + detected_User_Choice_Size);

                }
                break;
            case 9: {
                detected_User_Choice_Size = gridScale;
                //setEnglishWordsAndFrenchWords();
                scalable.generatePuzzle();
                scalable.scalablePuzzleGenerator();
                getGamePuzzle(scalable.gamePuzzle);
                workingPuzzle = originalPuzzle.clone();
                getSolutionPuzzle(scalable.solutionPuzzle);
                Log.d("okok", "Ok?");
                Log.d("wantSize", "the size:" + detected_User_Choice_Size);
            }
            break;
            case 12: {
                detected_User_Choice_Size = gridScale;
                //setEnglishWordsAndFrenchWords();
                scalable.generatePuzzle();
                scalable.scalablePuzzleGenerator();
                getGamePuzzle(scalable.gamePuzzle);
                workingPuzzle = originalPuzzle.clone();
                getSolutionPuzzle(scalable.solutionPuzzle);
                Log.d("okok", "Ok?");
                Log.d("wantSize", "the size:" + detected_User_Choice_Size);
            }break;
        }
    }
    String[] setEnglishWords(int gridSize){

        newEnglishWordsArray = new String[gridSize+1];
        for (int i = 0; i<gridSize+1;i++){
            newEnglishWordsArray [i] = englishWords[i];
        }
        return newEnglishWordsArray;
    }
    String[]setFrenchWords(int gridSize){
        newFrenchWordsArray = new String[gridSize+1];
        for (int i = 0; i<gridSize+1;i++){
            newFrenchWordsArray [i] = frenchWords[i];
        }
        return newFrenchWordsArray;
    }
    void getGamePuzzle(int [] inputPuzzle){
        for (int i = 0; i < detected_User_Choice_Size*detected_User_Choice_Size; i++) {
            originalPuzzle[i] = inputPuzzle[i];
        }
    }
    void getSolutionPuzzle (int [] inputPuzzle){
        for (int i = 0; i < detected_User_Choice_Size*detected_User_Choice_Size; i++) {
            solutionPuzzle[i] = inputPuzzle[i];
        }
    }
    void readPuzzlesFromInputStream(InputStream inputStream) {
        // This assumes each puzzle is on a separate line, as in .sdm format.
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = br.readLine()) != null) {
                int[] arr = convertPuzzleStringToArray(line);
                if (arr!=null) allPuzzles.add(arr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    Puzzles are sourced from http://forum.enjoysudoku.com/low-stepper-puzzles-t4200.html
     private int[] convertPuzzleStringToArray(String puzzleString) {
//        if (puzzleString.startsWith("#")) return null;
        int[] puzzleArray = new int[81];
        int puzzleSize=0;
        for (int j=0; puzzleSize<81 && j<puzzleString.length(); j++) {
            char c = puzzleString.charAt(j);
            if (Character.isDigit(c)) {
                puzzleArray[puzzleSize++] = Character.digit(c,10);
            } else if (c=='.') {
                puzzleArray[puzzleSize++] = 0;
            }
        }
        if (puzzleSize < 81) {
            Log.e("Parsing puzzle","Invalid string ("+puzzleSize+" digits)");
//            throw new Exception("Invalid puzzle string");
            return null;
        }
        return puzzleArray;
    }



    String[] getChoiceWords() {
        return Words[languageIndex];
    }

    String getWordAtPosition(int position) {
        if (isNotPreset(position)) {
            return Words[languageIndex][workingPuzzle[position]];
        }
        return Words[languageIndex^1][workingPuzzle[position]];
    }

    String getTranslationAtPosition(int position) {
        if (isNotPreset(position)) {
            return Words[languageIndex^1][workingPuzzle[position]];
        }
        return Words[languageIndex][workingPuzzle[position]];
    }

    void setValueAtPosition(int position, int value) {
        //assert (0<=value) && (value<=9);
        workingPuzzle[position] = value;
    }
    boolean isNotPreset(int position) {
        return originalPuzzle[position]==0;
    }

    void swapLanguage() {
        languageIndex ^= 1;
    }

    Locale getVoiceLocale() {
        return locales[languageIndex];
    }

    String getCurrentLanguage() {
        return languageNames[languageIndex];
    }

     void setPuzzle(int puzzleIndex) {
        // Check that puzzle index is valid.
        if (puzzleIndex < 0 || puzzleIndex > allPuzzles.size()) {
            Log.e("setPuzzle","puzzleIndex "+puzzleIndex+" invalid");
            return;
        }
        System.arraycopy(allPuzzles.get(currentPuzzleIndex), 0, originalPuzzle, 0, 81);
        System.arraycopy(originalPuzzle, 0, workingPuzzle, 0, 81);
    }

    void newPuzzle() {
        if (allPuzzles.size()==0) {
            Log.d("newPuzzle","No puzzles from file");
            return;
        }
//        Random random = new Random();
//        currentPuzzleIndex = random.nextInt(allPuzzles.size());
        currentPuzzleIndex = (currentPuzzleIndex + 1) % allPuzzles.size();
        setPuzzle(currentPuzzleIndex);
    }

    // Reset workingPuzzle to originalPuzzle
    void resetPuzzle() {

        for (int i = 0; i < workingPuzzle.length; i++) {
            workingPuzzle[i] = originalPuzzle[i];
      }
    }

    boolean checkSudokuCorrect() {
        boolean result = true;
        for (int regionNum = 0; regionNum < detected_User_Choice_Size; regionNum++) {
            result = result && !containsDuplicates(getRow(regionNum));
            result = result && !containsDuplicates(getColumn(regionNum));
            result = result && !containsDuplicates(getBox(regionNum));
        }
        return result;
    }

    boolean checkSudokuIncomplete() {
        for (int value : workingPuzzle) {
            if (value == 0) return true; // Incomplete
        }
        return false; // Puzzle is complete
    }

    int[] getRow(int rowNum) {
        int[] row = new int[detected_User_Choice_Size];
        for (int i = 0; i < detected_User_Choice_Size; i++) {
            row[i] = workingPuzzle[(i + rowNum * detected_User_Choice_Size)];
        }
        return row;
    }

    int[] getColumn(int columnNum) {
        int[] column = new int[detected_User_Choice_Size];
        for (int i = 0; i < detected_User_Choice_Size; i++) {
            column[i] = workingPuzzle[(columnNum + i * detected_User_Choice_Size)];
        }
        return column;
    }

    int[] getBox(int boxNum) {
        int[] box = new int[detected_User_Choice_Size];
        int boxWidth = (int) Math.sqrt(detected_User_Choice_Size);
        int firstRow = (boxNum - (boxNum % boxWidth));
        int firstCol = boxWidth * (boxNum % boxWidth);
        // Go through the box, left-to-right top-to-bottom.
        for (int row = 0; row < boxWidth; row++) {
            for (int col = 0; col < boxWidth; col++) {
                int position = (firstCol + col) + (firstRow + row) * detected_User_Choice_Size;
                box[col + boxWidth*row] = workingPuzzle[(position)];
            }
        }
        return box;
    }

    //    Check if rows and columns contain duplicates
    boolean containsDuplicates(int[] region) {
        boolean[] seen_yet = new boolean[detected_User_Choice_Size+1];
        for (int value : region) {
            if (seen_yet[value]) {
                return true; // we already saw this word
            }
            seen_yet[value] = true;
//            break;
        }
        return false;
    }



}
