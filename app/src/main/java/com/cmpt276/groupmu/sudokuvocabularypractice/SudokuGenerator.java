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

    SudokuGenerator(int puzzleSize, int emptySpotSize){
        this.sizeOfPuzzle = puzzleSize;
        this.numOfEmptyCells = emptySpotSize;
        this.puzzleSize = puzzleSize*puzzleSize;
        convertF2I = Math.sqrt (sizeOfPuzzle);
        sizeOfRegion = convertF2I.intValue();
        puzzle = new int [puzzleSize][puzzleSize];
    }
    void generatePuzzle(){
        int insertNum;
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
    boolean checkDuplicateInPuzzle(int row, int col, int n){
        return(checkDuplicateInBox(row-row%sizeOfRegion,col-col%sizeOfRegion,n) && checkDuplicateInColumn(col,n) && checkDuplicateInRow(row,n));
    }
    boolean checkDuplicateInBox(int rowNum, int colNum, int num)
    {
        for (int row = 0; row<sizeOfRegion; row++)
            for (int col = 0; col<sizeOfRegion; col++)
                if (puzzle[rowNum+row][colNum+col] == num)
                    return false;
        return true;
    }
    boolean checkDuplicateInRow (int rowNum, int checkNum){
        for (int i = 0; i < sizeOfPuzzle; i++){
            if (puzzle[rowNum][i] == checkNum){
                return false;
            }
        }
        return true;
    }
    boolean checkDuplicateInColumn (int colNum, int checkNum){
        for (int i = 0; i < sizeOfRegion; i ++){
            if (puzzle [i][colNum] == checkNum){
                return false;
            }
        }
        return true;
    }
    int random (int N){
        randomNum = Math.floor((Math.random()*N));
        int randomInt =  randomNum.intValue()+1;
        return  randomInt;
    }
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
    void buildSolutionPuzzle (){
        for (int i = 0;i<sizeOfPuzzle;i++ ){
            for (int j = 0; j<sizeOfPuzzle;j++){
                puzzleSolution.add(puzzle[i][j]);
            }
        }
    }
    void buildGamePuzzle(){
        for (int i = 0; i<sizeOfPuzzle; i++){
            for (int j = 0; j<sizeOfPuzzle;j++){
                puzzleForGame.add(puzzle[i][j]);
            }
        }
    }
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
