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
    private TextView gridtext;
    private final int[] originalPuzzle = {
            5, 4, 0,  0, 7, 0,  0, 0, 0,
            6, 0, 0,  1, 9, 5,  0, 0, 0,
            0, 9, 8,  0, 0, 0,  0, 6, 0,

            8, 0, 0,  0, 6, 0,  0, 0, 3,
            4, 0, 0,  8, 0, 3,  0, 0, 1,
            7, 0, 3,  0, 2, 0,  0, 0, 6,

            0, 6, 0,  0, 0, 0,  0, 8, 0,
            2, 0, 0,  4, 1, 9,  0, 0, 5,
            0, 4, 5,  0, 8, 0,  0, 7, 9
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
    private String currentItem = "";
    Button checkSudokuButton;
    Switch languageSwitch;
    Boolean switchState;
    private final String[] frenchWords = {"", "un", "deux", "trois", "quatre", "cinq", "six", "sept", "huit", "neuf"};
    private final String[] englishWords = {"", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
    private int[] savedPuzzle = new int[originalPuzzle.length];
    public String state;

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
        languageSwitch.setText("English");

        switchState = languageSwitch.isChecked();
//        savedPuzzle = Arrays.copyOf(Englishpuzzle, Englishpuzzle.length);
        generateGrid();
    }
//    Drop Down Menue
    public void dialogBuilder(final TextView set) {
        AlertDialog.Builder sudokuWords = new AlertDialog.Builder(this);
        sudokuWords.setTitle("Select the word to insert");
        dialogChoice = 0;
//        Check Language Mdde
        if(switchState == true){
            sudokuWords.setSingleChoiceItems(frenchWords, 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialogChoice = which;
                }
            });
        }
        else{
            sudokuWords.setSingleChoiceItems(englishWords, 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialogChoice = which;
                }
            });
        }
//        Set value to grid
        sudokuWords.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) throws ArrayIndexOutOfBoundsException {
                if (dialogChoice != -1) {
                    if(switchState == true){
                        mText = frenchWords[dialogChoice];
                    }else {
                        mText = englishWords[dialogChoice];
                    }

                    set.setText(mText);

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
        if(switchState == true){
            grid.setAdapter(new SudokuAdapter(this, workingPuzzle, englishWords));
        }else{
            grid.setAdapter(new SudokuAdapter(this, workingPuzzle, frenchWords));
        }



        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if (v == null)
                    return;
                else if (originalPuzzle[position] == 0) {
                    TextView textViewClicked = (TextView) v;
                    dialogBuilder(textViewClicked);
                } else{
                    makeAToast(position);

                }
                return;
                //Toast.makeText(this, "" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    //    Button Click methods
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.resetBtn:
                try {
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
        boolean result = false;
        for (int i = 0; i < grid.getCount(); i++) {
            if (workingPuzzle[i] == 0) {
                Log.d("false triggered", "false");
                Toast.makeText(this, "Sudoku is not completed yet", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        result = true;
        for (int regionNum = 0; regionNum < 9; regionNum++) {
            result = result && !containsDuplicates(getRow(regionNum));
            result = result && !containsDuplicates(getColumn(regionNum));
            result = result && !containsDuplicates(getBox(regionNum));
        }
        if (result == true) Toast.makeText(this,"Congratulation! Answer correct",Toast.LENGTH_SHORT).show();
        else Toast.makeText(this,"Sudoku not Correct",Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "Congratulation! Answer correct", Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "Sudoku not correct", Toast.LENGTH_SHORT).show();
    }


    private String getDisplayedText(int position) {
        // This is needed because only the TextView's content is modified, not
        // the puzzle array's content, and using getView() would reset those values.
        return ((TextView) grid.getChildAt(position)).getText().toString();
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
        for(int value : region){
            if (seen_yet[value]) {
                return true; // we already saw this word
            }
            seen_yet[value] = true;
//            break;
        }
        return false;
    }

//    Switch Language (French & English)
    public void changeLanguage(){
        if(switchState){
            switchState = false;
            state = "French";

        } else{
            switchState = true;
            state = "English";
        }
//        copyGrid();
        languageSwitch.setText(state);
        generateGrid();
        Toast.makeText(this, "Language Switched: " + state, Toast.LENGTH_SHORT).show();
    }

    public void makeAToast(int position){
        if(switchState){
            Toast.makeText(this, frenchWords[originalPuzzle[position]], Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, englishWords[originalPuzzle[position]], Toast.LENGTH_SHORT).show();
        }
    }
//    public void copyGrid() {
//        for (int i = 0; i < 81; i++) {
//            if (savedPuzzle[i] != "") {
//                savedPuzzle[i] = swapLanguage(switchState, savedPuzzle[i]);
//
//            }
//        }
//    }
//    public String swapLanguage(Boolean language, String word){
//        for(int i = 0; i < 9; i++){
//            if(savedPuzzle[i] != ""){
//                if(word == frenchWords[i]){
//                    return englishWords[i];
//                }else if(word == englishWords[i]){
//                    return frenchWords[i];
//                }
//                continue;
//            }
//
//        }
//        return null;
//    }
}
