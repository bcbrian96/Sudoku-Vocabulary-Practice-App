package com.cmpt276.groupmu.sudokuvocabularypractice;

import android.util.Pair;

import java.util.Stack;


/**
 * SudokuPuzzle class. Contains methods:
 * - Reading and converting from input streams of sudoku files
 * - Checking and verifying parameters of the sudoku
 */
class SudokuPuzzle {

    /** VARIABLES */
//    9x9
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
//    String[] gridSizeArray= {"4 x 4", "6 x 6","9 x 9", "12 x 12"};

    int user_selected_size;
    int size = 9;
    // Can be: 4, 6, 9, 12
    // boxes: 2x2, 2x3, 3x3, 3x4
    private int boxHeight = 3;
    private int boxWidth = 3;
    // boxHeight * boxWidth == size must be true

    int[] workingPuzzle = originalPuzzle.clone();
//    int[] solutionPuzzle;
    private int difficulty;

    /**
     * undoStack is a Stack of <position, previous_value>.
     * Documentation recommends using Deque, but that is less intuitive.
     * There may be slowdown because Stack (based on Vector) does
     * synchronization (requests a lock) for every operation.
     */
    private Stack<Pair<Integer,Integer>> undoStack = new Stack<>();
    int emptyCount; // Keep track of # empty cells (difficulty)

    /**
     * Constructor for SudokuPuzzle. Sets the size.
     * @param initial_size The initial size of the puzzle.
     */
    SudokuPuzzle(final int initial_size) {

        this.user_selected_size = initial_size;
        setPuzzleSize(initial_size);
    }

    /**
     * Set the puzzle size.
     * @param newSize The size of the puzzle (width and height).
     */
    void setPuzzleSize (int newSize){
        difficulty = (newSize*newSize)/3;
        size = newSize;
        switch (newSize) {
            case 4:
                boxWidth = 2;
                break;
            case 6:
            case 9:
                boxWidth = 3;
                break;
            case 12:
                boxWidth = 4;
                break;
        }
        boxHeight = newSize / boxWidth;
    }

    /**
     * Undoes the last move by popping previous state from undoStack.
     */
    void undoLastMove(){
        Pair<Integer, Integer> lastMove = undoStack.pop();
        if (lastMove != null) {
            setValueAt(lastMove.first, lastMove.second);
        }
    }

//    /**
//     * Reads the puzzles form the sudoku files
//     * @param inputStream   Input stream used to read from sudoku files
//     */
//    void readPuzzlesFromInputStream(InputStream inputStream) {
//        // This assumes each puzzle is on a separate line, as in .sdm format.
//        try {
//            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
//            String line;
//            while ((line = br.readLine()) != null) {
//                int[] arr = convertPuzzleStringToArray(line);
//                if (arr!=null) allPuzzles.add(arr);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    /**
//     * Converts string of puzzles to an array that is useable by the gridView. Sudokus puzzles are
//     * sourced from: http://forum.enjoysudoku.com/low-stepper-puzzles-t4200.html
//     * @param puzzleString  String of numbers to be converted to an array of integers
//     * @return  An array of integers to use in the puzzle gridview to display/organize the words
//     */
//    private int[] convertPuzzleStringToArray(String puzzleString) {
//        // Array to return
//        int[] puzzleArray = new int[81];
//
//        // Parse
//        int puzzleSize=0;
//        for (int j=0; puzzleSize<81 && j<puzzleString.length(); j++) {
//            char c = puzzleString.charAt(j);
//            if (Character.isDigit(c)) {
//                puzzleArray[puzzleSize++] = Character.digit(c,10);
//            } else if (c=='.') {
//                puzzleArray[puzzleSize++] = 0;
//            }
//        }
//
//        // Failed: Index out of bounds (too many characters)
//        if (puzzleSize < 81) {
//            Log.e("Parsing puzzle","Invalid string ("+puzzleSize+" digits)");
////            throw new Exception("Invalid puzzle string");
//            return null;
//        }
//        return puzzleArray;
//    }


    /**
     * Sets the value at the position in the Gridview
     * @param position  The position within the sudoku puzzle array
     * @param value     The value to set the puzzle index to
     */
    void setValueAt(final int position, final int value) {
        workingPuzzle[position] = value;
    }

    /**
     * First adds the move to the undoStack so it can be undone, then sets the value.
     * @param position  The position within the sudoku puzzle array
     * @param value     The value to set the puzzle index to
     */
    void setValueWithUndo(final int position, final int value) {
        // Record the position and the previous value there, before setting the new value.
        undoStack.push(new Pair<>(position, workingPuzzle[position]));
        setValueAt(position, value);
    }

    /**
     * Gets the value at a position in the puzzle.
     * @param position  The position within the sudoku puzzle array
     * @return          The value (number) at that position
     */
    int getValueAt(final int position) {
        return workingPuzzle[position];
    }

    /**
     * Checks if the position within the sudoku is not preset (not set to 0)
     * @param position  The position within the sudoku puzzle array
     * @return      True if the position is not preset, false otherwise
     */
    boolean isNotPreset(final int position) {
        return originalPuzzle[position]==0;
    }

    /**
     * Get the progress towards completion of the puzzle. Does not check correctness.
     * @return The number of cells filled in in the puzzle.
     */
    int getProgress() {
        int filled_in = 0;
        for (int i=0; i<workingPuzzle.length; i++) {
            if (workingPuzzle[i] != originalPuzzle[i]) {
                filled_in++;
            }
        }
        return filled_in;
    }

//    /**
//     * Checks that the puzzleIndex is within the bounds of the puzzle size and then copies the
//     * array to set the puzzle
//     * @param puzzleIndex   the index to set
//     */
//    private void setPuzzle(int puzzleIndex) {
//        // Check that puzzle index is valid.
//        if (puzzleIndex < 0 || puzzleIndex > allPuzzles.size()) {
//            Log.e("setPuzzle","puzzleIndex "+puzzleIndex+" invalid");
//            return;
//        }
//        System.arraycopy(allPuzzles.get(currentPuzzleIndex), 0, originalPuzzle, 0, 81);
//        System.arraycopy(originalPuzzle, 0, workingPuzzle, 0, 81);
//    }

    /**
     * Create a new random puzzle from a SudokuGenerator
     */
    void generateNewPuzzle() {
        SudokuGenerator scalable = new SudokuGenerator(size,difficulty);
        scalable.generatePuzzle();
        if (user_selected_size == 6 || user_selected_size ==12){
            originalPuzzle = scalable.gamePuzzle;
            workingPuzzle = originalPuzzle.clone();
        }
        else {
        scalable.scalablePuzzleGenerator();
        originalPuzzle = scalable.gamePuzzle;
        workingPuzzle = originalPuzzle.clone();}
//        solutionPuzzle = scalable.solutionPuzzle;
        // The old undo stack is now invalid, so clear it.
        undoStack.clear();
        // Count number of empty cells in new puzzle
        emptyCount = 0;
        for (int i : originalPuzzle) {
            if (i==0) emptyCount++;
        }
    }


    /**
     * Reset the puzzle
     */
    void resetPuzzle() {
        System.arraycopy(originalPuzzle, 0, workingPuzzle, 0, size*size);
        // The undo stack is now invalid, so clear it.
        undoStack.clear();
    }


    /**
     * Check if the puzzle is incorrect thus far
     * @return  A boolean value of true if the puzzle is incorrect so far, false otherwise
     */
    boolean checkSudokuIncorrect() {
        // If any rows/columns/boxes contain duplicates, sudoku is incorrect: return true.
        boolean result = false;
        for (int regionNum = 0; regionNum < size; regionNum++) {
            result = result || containsDuplicates(getRow(regionNum));
            result = result || containsDuplicates(getColumn(regionNum));
            result = result || containsDuplicates(getBox(regionNum));
        }
        return result;
    }

    /**
     * Checks if the sudoku is complete
     * @return  Returns true if the puzzle if incomplete, flase otherwise
     */
    boolean checkSudokuIncomplete() {
        for (int value : workingPuzzle) {
            if (value == 0) return true; // Incomplete
        }
        return false; // Puzzle is complete
    }

    /**
     * Gets the row of the sudoku
     * @param rowNum    Row number of the sudoku
     * @return  An array of integers for the puzzle row
     */
    int[] getRow(final int rowNum) {
        int[] row = new int[size];
        System.arraycopy(workingPuzzle, rowNum * size, row, 0, size);
        return row;
    }

    /**
     * Gets the column of the sudoku
     * @param columnNum Column number of the sudoku
     * @return  An array of integers for the puzzle column
     */
    int[] getColumn(final int columnNum) {
        int[] column = new int[size];
        for (int i = 0; i < size; i++) {
            column[i] = workingPuzzle[(columnNum + i * size)];
        }
        return column;
    }

    /**
     * Gets the box for a specific subset within sudoku.
     * Each box should only contain one of each number in the puzzle.
     * Boxes are (width * height): 2x2, 3x2, 3x3, 4x3 for puzzle sizes 4, 6, 9, 12.
     * @param boxNum    Box num from 0 - SIZE
     * @return  The box array of integers
     */
    int[] getBox(final int boxNum) {
        int[] box = new int[size];
        int boxesPerRow = size / boxWidth;
        int firstRow = (boxNum / boxesPerRow) * boxHeight;
        int firstCol = (boxNum % boxesPerRow) * boxWidth;
        // Go through the box, left-to-right top-to-bottom.
        for (int row = 0; row < boxHeight; row++) {
            for (int col = 0; col < boxWidth; col++) {
                int position = (firstCol + col) + (firstRow + row) * size;
                box[col + boxWidth*row] = workingPuzzle[(position)];
            }
        }
        return box;
    }

    /**
     * Checks if the sudoku region contains duplicates
     * @param region The region being checked within the sudoku
     * @return  A boolean value: true if there contains duplicates, false otherwise
     */
    boolean containsDuplicates(final int[] region) {
        boolean[] seen_yet = new boolean[size+1];
        for (int value : region) {
            if (value!=0 && seen_yet[value]) {
                return true; // we already saw this word
            }
            seen_yet[value] = true;
        }
        return false;
    }



}

//END
