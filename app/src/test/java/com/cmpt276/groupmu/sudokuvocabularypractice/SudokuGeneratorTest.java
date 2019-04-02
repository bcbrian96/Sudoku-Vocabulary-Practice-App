package com.cmpt276.groupmu.sudokuvocabularypractice;
import org.junit.Test;
import static org.junit.Assert.*;
public class SudokuGeneratorTest {
    private int[][] gamePuzzleForTest =
            {       {5, 4, 0, 0, 7, 0, 0, 0, 0},
                    {6, 0, 0, 1, 8, 5, 0, 0, 0},
                    {0, 8, 0, 0, 7, 0, 0, 6, 0},

                    {8, 0, 0, 0, 6, 0, 0, 0, 3},
                    {4, 0, 0, 8, 0, 3, 0, 0, 1},
                    {7, 0, 3, 0, 2, 0, 0, 0, 6},

                    {0, 6, 0, 0, 0, 0, 0, 8, 0},
                    {2, 0, 0, 4, 1, 8, 0, 0, 5},
                    {0, 4, 5, 0, 8, 0, 0, 7, 8}};

    private int[][] solutionPuzzleForTest =
            {{1, 2, 3, 4, 5, 6, 7, 8, 9},
                    {4, 5, 6, 7, 8, 9, 1, 2, 3},
                    {7, 8, 9, 1, 2, 3, 4, 5, 6},

                    {2, 3, 4, 5, 6, 7, 8, 9, 1},
                    {5, 6, 7, 8, 9, 1, 2, 0, 0},
                    {8, 9, 1, 2, 3, 4, 5, 6, 7},

                    {3, 4, 5, 6, 7, 8, 9, 1, 2},
                    {6, 7, 8, 9, 1, 2, 3, 4, 5},
                    {9, 1, 2, 3, 4, 5, 6, 7, 8}};
    private int[][] completePuzzle =
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
        Double actualRandom = tester.randomNum;
        assertNull(actualRandom);
    }

    @Test
    public void testCheckNumInBox() {
        SudokuGenerator tester = new SudokuGenerator(9, 20);
        for (int i = 0; i < tester.sizeOfPuzzle; i++) {
            System.arraycopy(gamePuzzleForTest[i], 0, tester.puzzle[i], 0, tester.sizeOfPuzzle);
        }
        boolean actual;
        actual = tester.checkDuplicateInBox(0, 0, 8);
        assertFalse(actual);
        actual = tester.checkDuplicateInBox(0, 3, 7);
        assertFalse(actual);
        actual = tester.checkDuplicateInBox(0, 6, 7);
        assertTrue(actual);
        actual = tester.checkDuplicateInBox(3, 0, 4);
        assertFalse(actual);
    }

    @Test
    public void testCheckNumInRow() {
        SudokuGenerator tester = new SudokuGenerator(9, 20);
        for (int i = 0; i < tester.sizeOfPuzzle; i++) {
            System.arraycopy(gamePuzzleForTest[i], 0, tester.puzzle[i], 0, tester.sizeOfPuzzle);
        }
        boolean actual = tester.checkDuplicateInRow(4, 4);
        assertFalse(actual);
    }

    @Test
    public void testCheckNumInColumn()

    {
        SudokuGenerator tester = new SudokuGenerator(9, 20);
        for (int i = 0; i < tester.sizeOfPuzzle; i++) {
            System.arraycopy(gamePuzzleForTest[i], 0, tester.puzzle[i], 0, tester.sizeOfPuzzle);
        }

        boolean actual = tester. checkDuplicateInColumn(5,7);
        assertTrue(actual);
    }
    @Test
    public void testRangeOfRandom (){
        SudokuGenerator tester = new SudokuGenerator(9, 20);
        int testNum = tester.random(tester.sizeOfPuzzle);
        boolean actual;
        if (testNum-1 == (tester.randomNum.intValue())){
            actual = testNum > 0 && testNum <= tester.sizeOfPuzzle;
        }
        else actual =false;
        assertTrue(actual);
    }
    @Test
    public void testSetRestOfBoxes(){
        // 9x9
        SudokuGenerator tester9 = new SudokuGenerator(9, 20);
        assertTrue(tester9.setRestBoxes(0,tester9.sizeOfRegion));
        assertTrue(tester9.setRestBoxes(9,9));
        //4x4
        SudokuGenerator tester4 = new SudokuGenerator(4, 10);
        assertTrue(tester4.setRestBoxes(0,tester4.sizeOfRegion));
        assertTrue(tester4.setRestBoxes(4,4));

        // 6x6
        SudokuGenerator tester6 = new SudokuGenerator(6, 20);
        assertTrue(tester6.setRestBoxes(0,tester6.sizeOfRegion));
        assertTrue(tester6.setRestBoxes(6,6));

        //12x12
        SudokuGenerator tester12 = new SudokuGenerator(12, 40);
        assertTrue(tester12.setRestBoxes(0,tester12.sizeOfRegion));
        assertTrue(tester12.setRestBoxes(12,12));
    }
    @Test
    public void testBuildGamePuzzle (){
        SudokuGenerator tester = new SudokuGenerator(9, 20);
        for (int i = 0; i < tester.sizeOfPuzzle; i++) {
            System.arraycopy(gamePuzzleForTest[i], 0, tester.puzzle[i], 0, tester.sizeOfPuzzle);
        }
        tester.buildGamePuzzle();
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
        assertTrue(actual);
    }
    @Test
    public void testBuildSolutionPuzzle (){
        SudokuGenerator tester = new SudokuGenerator(9, 20);
        for (int i = 0; i < tester.sizeOfPuzzle; i++) {
            System.arraycopy(solutionPuzzleForTest[i], 0, tester.puzzle[i], 0, tester.sizeOfPuzzle);
        }
        tester.buildSolutionPuzzle();
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
        assertTrue(actual);

    }
    @Test
    public void testEmptyCellMethod(){
        SudokuGenerator tester = new SudokuGenerator(9, 20);
        for (int i = 0; i < tester.sizeOfPuzzle; i++) {
            System.arraycopy(completePuzzle[i], 0, tester.puzzle[i], 0, tester.sizeOfPuzzle);
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
            System.arraycopy(gamePuzzleForTest[i], 0, tester.puzzle[i], 0, tester.sizeOfPuzzle);
        }
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
        assertFalse(actual);
    }

}
