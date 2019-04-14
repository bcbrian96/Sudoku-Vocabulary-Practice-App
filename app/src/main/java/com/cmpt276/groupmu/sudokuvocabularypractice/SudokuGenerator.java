package com.cmpt276.groupmu.sudokuvocabularypractice;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

class SudokuGenerator {
     int [][]puzzle ;
     int sizeOfPuzzle;
     int sizeOfRegion;
     int numOfEmptyCells;
    int puzzleSize;
    int [] gamePuzzle;
    int [] solutionPuzzle;
    Double convertF2I;
    Double randomNum;
      List<Integer> puzzleSolution = new ArrayList<Integer>();
      List<Integer> puzzleForGame = new ArrayList<Integer>();
    int [] default6x6Sudoku = {
            0,0,6, 1,5,0,
            1,5,0, 0,0,3,
            0,6,0, 5,3,2,
            2,0,5, 4,6,0,
            6,4,2, 3,1,5,
            5,0,0, 0,0,6
    };
    int [] default12x12Sudoku = {
            11,0,1,0, 0,0,7,0, 0,0,3,8,
            7,9,10,0, 0,0,12,0, 11,0,1,0,
            0,0,0,0, 11,0,0,6, 0,0,5,9,
            0,6,0,0, 9,8,0,12, 0,0,0,0,
            0,0,4,9, 0,0,6,0, 12,11,0,0,
            3,12,0,0, 4,0,0,0, 8,0,0,0,
            0,0,0,7, 0,0,0,8, 0,0,10,11,
            0,0,11,3, 0,6,0,0, 5,9,0,0,
            0,0,0,0, 2,0,3,11, 0,0,6,0,
            2,11,0,0, 12,0,0,10, 0,0,0,0,
            0,5,0,8, 0,1,0,0, 0,3,11,2,
            6,7,0,0, 0,11,0,0, 0,5,0,4,
    };
    /**
     * Constructor for Sudoku Generator class
     * @param puzzleSize    Size of the puzzle
     * @param emptySpotSize Number of spots in the puzzle not prefilled
     */
    SudokuGenerator(int puzzleSize, int emptySpotSize){
        this.sizeOfPuzzle = puzzleSize;
        this.numOfEmptyCells = emptySpotSize;
        this.puzzleSize = puzzleSize*puzzleSize;
        convertF2I = Math.sqrt (sizeOfPuzzle);
        sizeOfRegion = convertF2I.intValue();
        puzzle = new int [puzzleSize][puzzleSize];
    }

    /**
     * Function to generate puzzle
     */
    void generatePuzzle(){
        int insertNum;
        if (sizeOfPuzzle == 6){
            gamePuzzle = new int [6*6];
            for (int i = 0; i<36;i++ ){
               gamePuzzle[i]=default6x6Sudoku[i];
            }
            return;
        }
        if (sizeOfPuzzle == 12){
            gamePuzzle = new int [12*12];
            for (int i = 0; i<144;i++ ){
                gamePuzzle[i]=default12x12Sudoku[i];
            }
            return;
        }
        for (int i =0 ; i<sizeOfPuzzle; i+=sizeOfRegion){
            for (int j = 0; j<sizeOfRegion; j++){
                for (int k = 0; k < sizeOfRegion; k++){
                    do {
                        insertNum = random(sizeOfPuzzle);
                    }while (checkDuplicateInBox(i,i,insertNum) == false);
                    puzzle [i+j][i+k] =insertNum;
                }
            }
        }
        setRestBoxes(0,sizeOfRegion);
        buildSolutionPuzzle();
        emptyTheCells();
        buildGamePuzzle();

    }


    /**
     * Creating the solutionPuzzle and gamePuzzle for SudokuPuzzle class.
     */
    protected void scalablePuzzleGenerator(){
        gamePuzzle = new int[puzzleSize];
        solutionPuzzle = new int[puzzleSize];
        Integer[] tempArray2 = puzzleSolution.toArray(new Integer[puzzleSize]);
        for (int j = 0; j < puzzleSize;j++){
            solutionPuzzle [j] = tempArray2[j];
        }
        Integer[] tempArray1 = puzzleForGame.toArray(new Integer[puzzleSize]);
        for (int i = 0; i<puzzleSize;i++){
            gamePuzzle [i] = tempArray1[i];
        }

    }

    /**
     * Checks for duplicates in a row or column of a sudoku puzzle
     * @param row   row number of sudoku puzzle
     * @param col   column number of sudoku puzzle
     * @param n     The integer that holds the word in the sudoku puzzle array
     * @return      True if no duplicate in the puzzle, false otherwise
     */
    boolean checkDuplicateInPuzzle(int row, int col, int n){
        return(checkDuplicateInBox(row-row%sizeOfRegion,col-col%sizeOfRegion,n) && checkDuplicateInColumn(col,n) && checkDuplicateInRow(row,n));
    }

    /**
     * Checks for duplicates within the smaller box arrays
     * @param rowNum    Row number of the box
     * @param colNum    Column number of the box
     * @param num       The integer that holds the word in the specified box
     * @return          True if no duplicates in the box, false otherwise
     */
    boolean checkDuplicateInBox(int rowNum, int colNum, int num)
    {
        for (int row = 0; row<sizeOfRegion; row++)
            for (int col = 0; col<sizeOfRegion; col++)
                if (puzzle[rowNum+row][colNum+col] == num)
                    return false;
        return true;
    }

    /**
     * Checks for duplicates within a sudokupuzzle row
     * @param rowNum    Index within specified row
     * @param checkNum  The integer that holds the word in the row array
     * @return          True if no duplicates in the row, false otherwise
     */
    boolean checkDuplicateInRow (int rowNum, int checkNum){
        for (int i = 0; i < sizeOfPuzzle; i++){
            if (puzzle[rowNum][i] == checkNum){
                return false;
            }
        }
        return true;
    }

    /**
     * Checks for duplicates within a sudokupuzzle column
     * @param colNum    Index within specified column
     * @param checkNum  The integer that holds the word in the column array
     * @return          True if no duplicates in the column, false otherwise
     */
    boolean checkDuplicateInColumn (int colNum, int checkNum){
        for (int i = 0; i < sizeOfPuzzle; i ++){
            if (puzzle [i][colNum] == checkNum){
                return false;
            }
        }
        return true;
    }

    /**
     * Generates a random integer between 0 - 8
     * @param N The size of the squareroot of the sudoku puzzle array length
     *          ex: for 9x9 puzzle, n = 9
     * @return  returns a random integer within the specified bounds
     */
    int random (int N){
        randomNum = Math.floor((Math.random()*N));
        int randomInt =  randomNum.intValue()+1;
        return  randomInt;
    }

    /**
     * Set the values for the non diagonal boxes
     * @param rowIndex  Smallest row number of the specified box
     * @param colIndex  Smallest column number of the specified box
     * @return          True if successfully competed, false otherewise
     */
     boolean setRestBoxes(int rowIndex, int colIndex){
        if (rowIndex >= sizeOfPuzzle&& colIndex>=sizeOfPuzzle){ //Both index at the end
            return true;
        }
        if (rowIndex < sizeOfPuzzle - 1 && colIndex >= sizeOfPuzzle){ // Column number reach the end
            //goes to the next row
            colIndex = 0;
            rowIndex += 1;
        }
        if (rowIndex < sizeOfRegion && colIndex < sizeOfRegion) //Both indexes in the first box
            colIndex = sizeOfRegion;
        if (rowIndex < sizeOfPuzzle - sizeOfRegion){
            if (colIndex == (int)(rowIndex/sizeOfRegion)*sizeOfRegion)//looking for box with starting index 3,3
                colIndex += sizeOfRegion;
        }
        else{
            if (colIndex == sizeOfPuzzle - sizeOfRegion){
                rowIndex += 1;
                colIndex = 0;
                if (rowIndex >= sizeOfPuzzle)
                    return true;
            }
        }
        for (int i =1; i <=sizeOfPuzzle;i++){
            if (checkDuplicateInPuzzle(rowIndex,colIndex,i)){
                puzzle[rowIndex][colIndex] = i;
                if (setRestBoxes(rowIndex,colIndex+1)) return true;
                puzzle[rowIndex][colIndex] = 0;
            }
        }
        return false;
    }

    /**
     * Builds the sudoku puzzle solution (complete puzzle)
     */
    void buildSolutionPuzzle (){
        for (int i = 0;i<sizeOfPuzzle;i++ ){
            for (int j = 0; j<sizeOfPuzzle;j++){
                puzzleSolution.add(puzzle[i][j]);
            }
        }
    }

    /**
     * Builids the sudoku puzzle for the game (with empty cells for user input)
     */
    void buildGamePuzzle(){
        for (int i = 0; i<sizeOfPuzzle; i++){
            for (int j = 0; j<sizeOfPuzzle;j++){
                puzzleForGame.add(puzzle[i][j]);
            }
        }
    }

    /**
     * Empties cells from a complete puzzle solution to make a game puzzle.
     * Called in generatePuzzle().
     */
    public void emptyTheCells()
    {
        int countNum = numOfEmptyCells;
        while (countNum != 0)
        {
            int emptyCell = random(sizeOfPuzzle*sizeOfPuzzle)-1;
            int rowNum = (emptyCell/sizeOfPuzzle);
            int colNum = emptyCell%sizeOfPuzzle;

            if (puzzle[rowNum][colNum] != 0)
            {
                countNum --;
                puzzle[rowNum][colNum] = 0;
            }
        }
    }
}
