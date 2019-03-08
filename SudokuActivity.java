package com.cmpt276.groupmu.sudokuvocabularypractice;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SudokuActivity extends AppCompatActivity implements View.OnClickListener {

//    Global Variables
    private TextView gridtext;
    private final int[] originalPuzzle = {
            0, 0, 0,  2, 6, 0,  7, 0, 1,
            6, 8, 0,  0, 7, 0,  0, 9, 0,
            1, 9, 0,  0, 0, 4,  5, 0, 0,

            8, 2, 0,  1, 0, 0,  0, 4, 0,
            0, 0, 4,  6, 0, 2,  9, 0, 0,
            0, 5, 0,  0, 0, 3,  0, 2, 8,

            0, 0, 9,  3, 0, 0,  0, 7, 4,
            0, 4, 0,  0, 5, 0,  0, 3, 6,
            7, 0, 3,  0, 1, 8,  0, 0, 0
    };
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
    private final int[] workingPuzzle = originalPuzzle.clone();
    private GridView grid;
    Button resetButton;
    private String mText = "";
    private int dialogChoice;
    Button checkSudokuButton;
    Switch languageSwitch;
    int languageIndex;
    public String languageNames[] = {"French","English"};
    // Note: languageNames[] is in the opposite order of Words[].
    public String currentLanguage;


    private List<String> englishWords = new ArrayList<String>();
    private List<String> frenchWords = new ArrayList<String>();
    private String [][] Words;
   //private String[] frenchWords = {"", "un", "deux", "trois", "quatre", "cinq", "six", "sept", "huit", "neuf"};
   //private String[] englishWords = {"", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
   //private String[][] Words = {englishWords, frenchWords};

    private Integer[] numHints;


//    Initialization
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku);
        grid = findViewById(R.id.grid);
        resetButton = findViewById(R.id.resetBtn);
        resetButton.setOnClickListener(this);
        checkSudokuButton = findViewById(R.id.checkSudoku);
        checkSudokuButton.setOnClickListener(this);
        languageSwitch = (Switch) findViewById(R.id.language_switch);
        languageSwitch.setOnClickListener(this);
        languageSwitch.setChecked(true);
        languageIndex = 1;
        currentLanguage = languageNames[languageIndex];
        languageSwitch.setText(currentLanguage);


        readData();
        generateGrid();
    }

    private void readData() {
        InputStream is = getResources().openRawResource(R.raw.words);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String line;
        englishWords.add("");
        frenchWords.add("");
        try{
            while ( (line = reader.readLine()) != null) {

                String[] tokens = line.split(",");
                englishWords.add(tokens[0]);
                frenchWords.add(tokens[1]);
                Log.d("MyActivity", "Just added: " + tokens[0] + "AND" + tokens[1]);
                Log.d("MyActivity", "Length of array = " + englishWords.size() + "AND" + frenchWords.size());

            }
        } catch (IOException e){
            e.printStackTrace();
        }

        String english[] = new String[englishWords.size()];
        english = (String[]) englishWords.toArray(english);
        String french[] = new String[frenchWords.size()];
        french = (String[]) frenchWords.toArray(french);

        Words = new String[][]{english, french};

        numHints = new Integer[englishWords.size()]; //for number of hints asked by user
        Arrays.fill(numHints,0);
    }


    //    Drop Down Menue
    public void dialogBuilder(final TextView set, final int position) {
        AlertDialog.Builder sudokuWords = new AlertDialog.Builder(this);
        sudokuWords.setTitle("Select the word to insert");
        dialogChoice = 0;
//        Check Language Mdde
        sudokuWords.setSingleChoiceItems(Words[languageIndex], 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogChoice = which;
            }
        });
//        Set value to grid
        sudokuWords.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) throws ArrayIndexOutOfBoundsException {
                if (dialogChoice != -1) {
                    mText = Words[languageIndex][dialogChoice];

                    set.setText(mText);
                    workingPuzzle[position] = dialogChoice;

                }
            }
        });
//
        sudokuWords.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        sudokuWords.show();
//        Toast.makeText(this, mText, Toast.LENGTH_SHORT).show();
    }
//    Generate Grid
    public void generateGrid() {

        grid.setAdapter(new SudokuAdapter(this, workingPuzzle, originalPuzzle, Words, languageIndex));
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if (v == null)
                    return;
                else if (originalPuzzle[position] == 0) {
                    dialogBuilder((TextView) v, position); // Choose a value for the cell.
                } else {
                    hintPresetCellTranslation(position);

                    Log.d("MyActivity", "Hint Position: " + position);
                    numHints[originalPuzzle[position]]++;
                    Log.d("MyActivity", "NumHint Value: " + numHints[originalPuzzle[position]]);

                }
            }
        });
    }

    //    Button Click methods
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.resetBtn:
                try {
                    resetPuzzle();
                    generateGrid();
                } catch (Exception e) {
                    Log.d("Can not reset", " " + e);
                }
                break;
            case R.id.checkSudoku:
                try {
                    checkSudoku();
                } catch (Exception e) {
                    Log.d("Check Sudoku error", "" + e);
                }
                break;
            case R.id.language_switch:
                try {
                    changeLanguage();
                } catch (Exception e) {
                    Log.d("Check switch eror", "" + e);
                }
                break;

        }
    }
    // Reset workingPuzzle to originalPuzzle
    public void resetPuzzle() {
        for (int i = 0; i < workingPuzzle.length; i++) {
            workingPuzzle[i] = originalPuzzle[i];
        }
    }
//    Check Sudoku solutions
    public void checkSudoku() {
        if (checkSudokuIncomplete()) {
            Log.d("checkSudoku", "sudoku incomplete");
            Toast.makeText(this, "Sudoku is not completed yet", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean result = true;
        for (int regionNum = 0; regionNum < 9; regionNum++) {
            result = result && !containsDuplicates(getRow(regionNum));
            result = result && !containsDuplicates(getColumn(regionNum));
            result = result && !containsDuplicates(getBox(regionNum));
        }
        if (result) Toast.makeText(this,"Congratulation! Answer correct",Toast.LENGTH_SHORT).show();
        else Toast.makeText(this,"Sudoku not Correct",Toast.LENGTH_SHORT).show();
    }

    public boolean checkSudokuIncomplete() {
        for (int value : workingPuzzle) {
            if (value == 0) return true; // Incomplete
        }
        return false; // Puzzle is complete
    }

    public int[] getRow(int rowNum) {
        int[] row = new int[9];
        for (int i = 0; i < 9; i++) {
            row[i] = workingPuzzle[(i + rowNum * 9)];
        }
        return row;
    }

    public int[] getColumn(int columnNum) {
        int[] column = new int[9];
        for (int i = 0; i < 9; i++) {
            column[i] = workingPuzzle[(columnNum + i * 9)];
        }
        return column;
    }

    public int[] getBox(int boxNum) {
        int[] box = new int[9];
        int firstRow = (boxNum - (boxNum % 3));
        int firstCol = 3 * (boxNum % 3);
        // Go through the box, left-to-right top-to-bottom.
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                int position = (firstCol + col) + (firstRow + row) * 9;
                box[col + 3*row] = workingPuzzle[(position)];
            }
        }
        return box;
    }

//    Check if rows and columns contain duplicates
    public boolean containsDuplicates(int[] region) {
        boolean[] seen_yet = new boolean[10];
        for (int value : region) {
            if (seen_yet[value]) {
                return true; // we already saw this word
            }
            seen_yet[value] = true;
//            break;
        }
        return false;
    }

//    Switch Language (French & English)
    public void changeLanguage() {
        languageIndex ^= 1;
        currentLanguage = languageNames[languageIndex];
        languageSwitch.setText(currentLanguage);
        generateGrid();
        Toast.makeText(this, "Language Switched: " + currentLanguage, Toast.LENGTH_SHORT).show();
    }

    public void hintPresetCellTranslation(int position) {
        Toast.makeText(this, Words[languageIndex][originalPuzzle[position]], Toast.LENGTH_SHORT).show();
    }
}
