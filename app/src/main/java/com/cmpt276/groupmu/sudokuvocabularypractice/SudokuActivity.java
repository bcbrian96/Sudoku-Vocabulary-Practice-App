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

import java.util.Arrays;

public class SudokuActivity extends AppCompatActivity implements View.OnClickListener {

//    Global Variables
    public SudokuPuzzle puzzle;
    private TextView gridtext;
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
    private final String[] frenchWords = {"", "un", "deux", "trois", "quatre", "cinq", "six", "sept", "huit", "neuf"};
    private final String[] englishWords = {"", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
    private final String[][] Words = {englishWords, frenchWords};

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
        puzzle = new SudokuPuzzle();

        generateGrid();
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
                    puzzle.workingPuzzle[position] = dialogChoice;
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
        grid.setAdapter(new SudokuAdapter(this, puzzle.workingPuzzle, puzzle.originalPuzzle, Words, languageIndex));


        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if (v == null)
                    return;
                else if (puzzle.originalPuzzle[position] == 0) {
                    dialogBuilder((TextView) v, position); // Choose a value for the cell.
                } else {
                    hintPresetCellTranslation(position);
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
                    puzzle.resetPuzzle();
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

//    Check Sudoku solutions
    public void checkSudoku() {
        if (puzzle.checkSudokuIncomplete()) {
            Log.d("checkSudoku", "sudoku incomplete");
            Toast.makeText(this, "Sudoku is not completed yet", Toast.LENGTH_SHORT).show();
            return;
        }
        if (puzzle.checkSudokuCorrect())
        Toast.makeText(this,"Congratulation! Answer correct",Toast.LENGTH_SHORT).show();
        else Toast.makeText(this,"Sudoku not Correct",Toast.LENGTH_SHORT).show();
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
        Toast.makeText(this, Words[languageIndex][puzzle.originalPuzzle[position]], Toast.LENGTH_SHORT).show();
    }
}
