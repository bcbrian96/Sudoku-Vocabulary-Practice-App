package com.cmpt276.groupmu.sudokuvocabularypractice;
import org.junit.Test;
import static org.junit.Assert.*;
public class SudokuGeneratorTest {
    int[][] gamePuzzleForTest =
            {       {5, 4, 0, 0, 7, 0, 0, 0, 0},
                    {6, 0, 0, 1, 8, 5, 0, 0, 0},
                    {0, 8, 0, 0, 7, 0, 0, 6, 0},

                    {8, 0, 0, 0, 6, 0, 0, 0, 3},
                    {4, 0, 0, 8, 0, 3, 0, 0, 1},
                    {7, 0, 3, 0, 2, 0, 0, 0, 6},

                    {0, 6, 0, 0, 0, 0, 0, 8, 0},
                    {2, 0, 0, 4, 1, 8, 0, 0, 5},
                    {0, 4, 5, 0, 8, 0, 0, 7, 8}};

    int[][] solutionPuzzleForTest =
            {{1, 2, 3, 4, 5, 6, 7, 8, 9},
                    {4, 5, 6, 7, 8, 9, 1, 2, 3},
                    {7, 8, 9, 1, 2, 3, 4, 5, 6},

                    {2, 3, 4, 5, 6, 7, 8, 9, 1},
                    {5, 6, 7, 8, 9, 1, 2, 0, 0},
                    {8, 9, 1, 2, 3, 4, 5, 6, 7},

                    {3, 4, 5, 6, 7, 8, 9, 1, 2},
                    {6, 7, 8, 9, 1, 2, 3, 4, 5},
                    {9, 1, 2, 3, 4, 5, 6, 7, 8}};
    int[][] completePuzzle =
            {{1, 2, 3, 4, 5, 6, 7, 8, 9},
                    {4, 5, 6, 7, 8, 9, 1, 2, 3},
                    {7, 8, 9, 1, 2, 3, 4, 5, 6},

                    {2, 3, 4, 5, 6, 7, 8, 9, 1},
                    {5, 6, 7, 8, 9, 1, 2, 3, 4},
                    {8, 9, 1, 2, 3, 4, 5, 6, 7},

                    {3, 4, 5, 6, 7, 8, 9, 1, 2},
                    {6, 7, 8, 9, 1, 2, 3, 4, 5},
                    {9, 1, 2, 3, 4, 5, 6, 7, 8}};

    @Test
    public void variablesRangeTest() {
        SudokuGenerator tester = new SudokuGenerator(9, 20);
        int expectSizeOfPuzzle = 9;
        int actualSizeOfPuzzle = tester.sizeOfPuzzle;
        assertEquals(expectSizeOfPuzzle, actualSizeOfPuzzle);
        int expectEmptySpotSize = 20;
        int actualEmptySpotSize = tester.numOfEmptyCells;
        assertEquals(expectEmptySpotSize, actualEmptySpotSize);
        int expectPuzzleSize = 81;
        int actualPuzzleSize = tester.puzzleSize;
        assertEquals(expectPuzzleSize, actualPuzzleSize);
        Double expectConvertF2I = 3.0;
        Double actualConvertF2I = tester.convertF2I;
        assertEquals(expectConvertF2I, actualConvertF2I);
        int expectBoxWidth = 3;
        int actualBoxWidth = tester.sizeOfRegion;
        assertEquals(expectBoxWidth, actualBoxWidth);
        int expectPuzzleRange = 9;
        int actualRowPuzzleRange = tester.puzzle[0].length;
        assertEquals(expectPuzzleRange, actualRowPuzzleRange);
        int actualColPuzzleRange = tester.puzzle.length;
        assertEquals(expectPuzzleRange, actualColPuzzleRange);
        Double expectRandom = null;
        Double actualRandom = tester.randomNum;
        assertEquals(expectRandom,actualRandom);
    }

    @Test
    public void testCheckNumInBox() {
        SudokuGenerator tester = new SudokuGenerator(9, 20);
        for (int i = 0; i < tester.sizeOfPuzzle; i++) {
            for (int j = 0; j < tester.sizeOfPuzzle; j++) {
                tester.puzzle[i][j] = gamePuzzleForTest[i][j];
            }
        }
        boolean expect = false;
        boolean actual;
        actual = tester.checkDuplicateInBox(0, 0, 8);
        assertEquals(expect, actual);
        expect = false;
        actual = tester.checkDuplicateInBox(0, 3, 7);
        assertEquals(expect, actual);
        expect = true;
        actual = tester.checkDuplicateInBox(0, 6, 7);
        assertEquals(expect, actual);
        expect = false;
        actual = tester.checkDuplicateInBox(3, 0, 4);
        assertEquals(expect, actual);
    }

    @Test
    public void testCheckNumInRow() {
        SudokuGenerator tester = new SudokuGenerator(9, 20);
        for (int i = 0; i < tester.sizeOfPuzzle; i++) {
            for (int j = 0; j < tester.sizeOfPuzzle; j++) {
                tester.puzzle[i][j] = gamePuzzleForTest[i][j];
            }
        }
        boolean expect = false;
        boolean actual = tester.checkDuplicateInRow(4, 4);
        assertEquals(expect, actual);
    }

    @Test
    public void testCheckNumInColumn()

    {
        SudokuGenerator tester = new SudokuGenerator(9, 20);
        for (int i = 0; i < tester.sizeOfPuzzle; i++) {
            for (int j = 0; j < tester.sizeOfPuzzle; j++) {
                tester.puzzle[i][j] = gamePuzzleForTest[i][j];
            }
        }
        boolean expect = true;
        boolean actual = tester. checkDuplicateInColumn(5,7);
        assertEquals(expect,actual);
    }
    @Test
    public void testRangeOfRandom (){
        SudokuGenerator tester = new SudokuGenerator(9, 20);
        int testNum = tester.random(tester.sizeOfPuzzle);
        boolean expect = true;
        boolean actual;
        if (testNum-1 == (tester.randomNum.intValue())){
        if (testNum <= 0 || testNum >tester.sizeOfPuzzle){
            actual = false;
        }
        else actual =true;}
        else actual =false;
        assertEquals(expect,actual);
    }
    @Test
    public void testSetRestOfBoxes(){
        SudokuGenerator tester = new SudokuGenerator(9, 20);
        boolean expect = true;
        boolean actual = tester.setRestBoxes(0,tester.sizeOfRegion);
        assertEquals(expect,actual);
    }
    @Test
    public void testBuildGamePuzzle (){
        SudokuGenerator tester = new SudokuGenerator(9, 20);
        for (int i = 0; i < tester.sizeOfPuzzle; i++) {
            for (int j = 0; j < tester.sizeOfPuzzle; j++) {
                tester.puzzle[i][j] = gamePuzzleForTest[i][j];
            }
        }
        tester.buildGamePuzzle();
        boolean expect = true;
        boolean actualTemp = true;
        int [] temp = {5, 4, 0, 0, 7, 0, 0, 0, 0,
                6, 0, 0, 1, 8, 5, 0, 0, 0,
                0, 8, 0, 0, 7, 0, 0, 6, 0,

                8, 0, 0, 0, 6, 0, 0, 0, 3,
                4, 0, 0, 8, 0, 3, 0, 0, 1,
                7, 0, 3, 0, 2, 0, 0, 0, 6,

                0, 6, 0, 0, 0, 0, 0, 8, 0,
                2, 0, 0, 4, 1, 8, 0, 0, 5,
                0, 4, 5, 0, 8, 0, 0, 7, 8};
        Integer[] gamePuzzleTemp = tester.puzzleForGame.toArray(new Integer[tester.puzzleSize]);
        for (int i = 0;i<tester.puzzleSize;i++ ){
            if(gamePuzzleTemp[i]!=temp[i]){
                 actualTemp = false;
                 break;
            }
        }

        boolean actual = actualTemp;
        assertEquals(expect,actual);
    }
    @Test
    public void testBuildSolutionPuzzle (){
        SudokuGenerator tester = new SudokuGenerator(9, 20);
        for (int i = 0; i < tester.sizeOfPuzzle; i++) {
            for (int j = 0; j < tester.sizeOfPuzzle; j++) {
                tester.puzzle[i][j] = solutionPuzzleForTest[i][j];
            }
        }
        tester.buildSolutionPuzzle();
        boolean expect = true;
        boolean actualTemp =true;
        int[] temp =
                {1, 2, 3, 4, 5, 6, 7, 8, 9,
                        4, 5, 6, 7, 8, 9, 1, 2, 3,
                        7, 8, 9, 1, 2, 3, 4, 5, 6,

                        2, 3, 4, 5, 6, 7, 8, 9, 1,
                        5, 6, 7, 8, 9, 1, 2, 0, 0,
                        8, 9, 1, 2, 3, 4, 5, 6, 7,

                        3, 4, 5, 6, 7, 8, 9, 1, 2,
                        6, 7, 8, 9, 1, 2, 3, 4, 5,
                        9, 1, 2, 3, 4, 5, 6, 7, 8};
        Integer[] solutionPuzzleTemp = tester.puzzleSolution.toArray(new Integer[tester.puzzleSize]);
        for (int i = 0;i<tester.puzzleSize;i++ ){
            if(solutionPuzzleTemp[i]!=temp[i]){
                actualTemp = false;
                break;
            }
        }

        boolean actual = actualTemp;
        assertEquals(expect,actual);

    }
    @Test
    public void testEmptyCellMethod(){
        SudokuGenerator tester = new SudokuGenerator(9, 20);
        for (int i = 0; i < tester.sizeOfPuzzle; i++) {
            for (int j = 0; j < tester.sizeOfPuzzle; j++) {
                tester.puzzle[i][j] = completePuzzle[i][j];
            }
        }
        tester.emptyTheCells();
        int expect = 20;
        int emptyCellCount=0;
        for (int i = 0; i < tester.sizeOfPuzzle; i++) {
            for (int j = 0; j < tester.sizeOfPuzzle; j++) {
                if (tester.puzzle[i][j] == 0)
                    emptyCellCount++;
            }
        }
        int actual = emptyCellCount;
        assertEquals(expect,actual);
    }
    @Test
    public void testGeneratePuzzle (){
        SudokuGenerator tester = new SudokuGenerator(9, 20);
        tester.generatePuzzle();
        int expectElementCount = 81;
        int actualELementCount = tester.puzzle.length*tester.puzzle[0].length;
        assertEquals( expectElementCount,actualELementCount);
    }
    @Test
    public void testScalablePuzzleGenerator(){
        SudokuGenerator tester = new SudokuGenerator(9, 20);
        tester.generatePuzzle();
        tester.scalablePuzzleGenerator();
        int gamePuzzleRange = tester.gamePuzzle.length;
        int expectGamePuzzleRange = 81;
        assertEquals(gamePuzzleRange,expectGamePuzzleRange);
        int solutionPuzzleRange = tester.solutionPuzzle.length;
        int expectSolutionPuzzleRange = 81;
        assertEquals(expectSolutionPuzzleRange,solutionPuzzleRange);
    }
    @Test
    public void testCheckDuplicateInPuzzle(){
        SudokuGenerator tester = new SudokuGenerator(9, 20);
        for (int i = 0; i < tester.sizeOfPuzzle; i++) {
            for (int j = 0; j < tester.sizeOfPuzzle; j++) {
                tester.puzzle[i][j] = gamePuzzleForTest[i][j];
            }
        }
        boolean expect = false;
        boolean actual = true;
        for (int i =0; i<tester.sizeOfPuzzle;i++){
            for (int j =0; j<tester.sizeOfPuzzle;j++){
                for (int k=1; k <=tester.sizeOfPuzzle; k++){
                    if (!tester.checkDuplicateInPuzzle(i,j,k)){
                        actual = false;
                        break;
                    }
                }
            }
        }
        assertEquals(expect,actual);
    }

}
